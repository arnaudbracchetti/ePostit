/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.abi.epostit.serial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author abracchetti
 */
public class InternalKanban {
    private Map<String, List> kanban = new HashMap<String, List>();
    
    public List getColumnContent(String colName)
    {
        return kanban.get(colName);
    }        
            
    public Set<String> getColumnsName()
    {
        return kanban.keySet();
    }
    
    public void addPostit(String col, Integer postitId)
    {
        if(!kanban.containsKey(col))
            kanban.put(col, new ArrayList<Integer>());
        
        kanban.get(col).add(postitId);
    }
    
    public void clear()
    {
        kanban.clear();
    }
    
}

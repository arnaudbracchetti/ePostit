/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.abi.epostit.serial;

import java.util.Map;

/**
 *
 * @author Arnaud
 */
public interface PostitSerialNotification {
    
    public abstract void notifyPostit(Map<Integer,Integer> kanban);
    
}

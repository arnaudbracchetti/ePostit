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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe de gestion des message reçu via le port serie de la part du hardware.
 * Cette classe fournit tous les service permétant l'interprétation et la 
 * gestion des messages.
 * 
 * @author abracchetti
 */
public class SerialMessage {
    
    /**
     * Zone de construction des messages
     */
    private StringBuffer tempMsg = new StringBuffer(20);
    
    private Map<String, List> kanban = new HashMap<String, List>();
    
    private final Pattern MessagePattern = Pattern.compile("<.*?>");
    private final Pattern ColumnPattern = Pattern.compile("(\\d+):((\\d+,?)*);?");
    private final Pattern ColumncontentPattern = Pattern.compile("(\\d+),?");
    
    
    
    /**
     * methode de construction des message issues de la liaison série.
     * Le message est construit à mesure des appels succéssifs à cette méthode.
     * Une fois qu'un message complet est détecté, il est automatiquement 
     * analysé.
     * 
     * Note : un message analysé est supprimé de la zone de construction
     * 
     * @param msgPart moceau de message à ajouter au message en cour de construction
     * @return retourne true si un message complet à put être traité.
     */
    public boolean buildMessage(String msgPart)
    {
        tempMsg.append(msgPart);
        
        String msg = getCompleteMsg();
        if (!msg.isEmpty())
        {
            interpretMsg(msg);
            return true;
        }
        else
        {
            return false;
        }
        
    }       

    /**
     * Cette methode scan le message en cours de construction à la recherche 
     * d'un message complet. 
     * Si un message est trouvé il est retrourné et est supprimé de la de la 
     * zone de construction. 
     * Si aucun message complet n'est retrouvé la methode retroune une chaine vide
     * 
     * @return le message complet s'il est trouvé dans la zone de construction.
     */
    private String getCompleteMsg() 
    {
        String ret = "";
        Matcher matcher = MessagePattern.matcher(tempMsg);
        
        if(matcher.find())
        {
            ret = matcher.group();
            tempMsg = new StringBuffer(matcher.replaceFirst(""));
        }
        
        return ret;
              
    }

    
    /**
     * cette methode décompose un message complet pour permettre une utilisation
     * des données qu'il conteint au travers de la classe SerialMessage.
     * 
     * @param msg message complet 
     */
    private void interpretMsg(String msg) 
    {
        Matcher matcher = ColumnPattern.matcher(msg);
        
        while(matcher.find())
        {
            String colName = matcher.group(1);
            String colContentStr = matcher.group(2);
            Matcher matcherColContent = ColumncontentPattern.matcher(colContentStr);
            List<Integer> colContentLst = new ArrayList();
            
            while(matcherColContent.find())
            {
                //Integer postitNum = matcherColContent.groupCount();
                colContentLst.add(Integer.parseInt(matcherColContent.group(1)));
                
            }
            kanban.put(colName, colContentLst);
        }
    }

    public String getBuidingZone() {
        return new String(tempMsg);
    }
    
    public List getColumnContent(String colName)
    {
        return kanban.get(colName);
    }        
            
    public Set<String> getColumnsName()
    {
        return kanban.keySet();
    }
    
}

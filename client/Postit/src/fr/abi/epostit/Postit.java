/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.abi.epostit;


import fr.abi.epostit.serial.PostitSerial;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 *
 * @author Arnaud
 */
public class Postit extends Application {
    
    
    
    @Override
    public void start(Stage stage) throws Exception {
        
      
        PostitSerial serial = new PostitSerial("COM4");  // TODO gerer correctement le nom du port à ouvrir
        
        Pane root = FXMLLoader.load(getClass().getResource("FXMLMain.fxml"));
        
        
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
    String message = "<1:12,3>";
    
    Pattern pattern = Pattern.compile("<(\\d+):((\\d+),?)+>");
    Matcher matcher = pattern.matcher(message);
    matcher.find();
    
    for(int i=0;i<matcher.groupCount();i++)
    {
        System.out.println("toto");
        System.out.println(matcher.group(i));
    }
      
        
    //launch(args);
    }
    
    
    
    
}

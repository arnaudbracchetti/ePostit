/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.abi.epostit;


import fr.abi.epostit.serial.PostitSerial;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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
        
       
      
        
    launch(args);
    }
    
    
    
    
}

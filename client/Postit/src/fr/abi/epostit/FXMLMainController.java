/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.abi.epostit;

import fr.abi.epostit.serial.InternalKanban;
import fr.abi.epostit.serial.PostitSerial;
import fr.abi.epostit.serial.SerialMessage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Arnaud
 */
public class FXMLMainController implements Initializable {
    
    @FXML
    private Label label;
    
     @FXML
    private VBox Col1;

    @FXML
    private VBox col2;
    

    private VBox col[];
    private PostitSerial serial;
    private SerialMessage message;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
        
            message = new SerialMessage();
            serial = new PostitSerial("COM4", this::fillKanban);
            
            Pane postit = FXMLLoader.load(getClass().getResource("FXMLPostit.fxml"));
            Col1.getChildren().add(postit);
            postit = FXMLLoader.load(getClass().getResource("FXMLPostit.fxml"));
            Col1.getChildren().add(postit);
        }    
        catch(IOException e)
        {
        
        }
    }
    
    private void fillKanban(InternalKanban kanban)
    {
        
    }
    
}

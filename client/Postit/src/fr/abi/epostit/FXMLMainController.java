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
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
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
    
    @FXML
    private Menu portMenu;
    

    private VBox col[] = {Col1, col2};
    private PostitSerial serial;
    private String selectedPortName = "";
    
    @FXML  
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        String[] ports = PostitSerial.getPorts();
        ToggleGroup group = new ToggleGroup();
        boolean isFirst = true;
        for(String port : ports)
        {
            RadioMenuItem menuItem = new RadioMenuItem(port);
            menuItem.setToggleGroup(group);
            menuItem.setOnAction((e)->HandleSelectedPortName(e));
            portMenu.getItems().add(menuItem);
            
            if (isFirst)
            {
                menuItem.selectedProperty().set(true);
                selectedPortName = port;
                isFirst = false;
            }
            else
            {
                menuItem.selectedProperty().set(false);
            }
        }
        
        
        serial = new PostitSerial(this::fillKanban); 
        serial.openPort(selectedPortName);
    }
    
    private void HandleSelectedPortName(ActionEvent e)
    {
        RadioMenuItem menuItem = (RadioMenuItem)e.getSource();
        selectedPortName = menuItem.getText();
        serial.openPort(selectedPortName);
    }
    
    private void fillKanban(InternalKanban kanban)
    {
        fillColumn(1,kanban.getColumnContent("1"));
        fillColumn(2,kanban.getColumnContent("2"));
        
    }
    
    private void fillColumn(int colNum, List<Integer> postitLst)
    {
        Pane postit;
        
        try {
            for(Integer postitId : postitLst)
            {
                postit = FXMLLoader.load(getClass().getResource("FXMLPostit.fxml"));
                col[colNum-1].getChildren().add(postit);
            }
        }
        catch(IOException e)
        {
            //TODO : gere l'exception
        }
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.abi.epostit;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;


/**
 * FXML Controller class
 *
 * @author Arnaud
 */
public class FXMLPostitController implements Initializable {
    public int a;
    @FXML
    private Label PostitNumber;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         a=1;
    }    

    void setPostitNumber(String num)
    {
        PostitNumber.setText(num);
    }
    
}

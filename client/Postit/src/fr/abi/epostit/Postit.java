/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.abi.epostit;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPortEvent;

/**
 *
 * @author Arnaud
 */
public class Postit extends Application {
    
    private SerialPort sPort;
    
    @Override
    public void start(Stage stage) throws Exception {
        
        serial();  
        
        
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
    
    private void serial()
    {
        String[] ports = SerialPortList.getPortNames();
        sPort = new SerialPort("COM4");
        
        try {
        sPort.openPort();    
        sPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        sPort.addEventListener(e-> {this.TheEventHandler(e);});
        
        }
        catch(SerialPortException e)
        {
            System.out.println(e.getMethodName()+" : "+ e.getExceptionType());
        } 
    }
    
    private void TheEventHandler(SerialPortEvent e)
    {
        try 
        {
            if (e.isRXCHAR())
            {
                System.out.println(sPort.readString());
            }
        
        }
        catch(SerialPortException exp)
        {
            System.out.println(exp.getMethodName()+" : "+ exp.getExceptionType());
        } 
    }
}

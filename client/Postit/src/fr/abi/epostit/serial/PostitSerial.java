/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.abi.epostit.serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;

/**
 * Classe de gestion de la liaison serie avec la partie hardware de l'ePostit
 * @author abracchetti
 */
public class PostitSerial {
    
    private SerialPort sPort;
    private StringBuffer hardwareMessage = new StringBuffer(10);
    
    public PostitSerial(String portName)
    {
        sPort = new SerialPort("COM4");
        
        try 
        {
            sPort.openPort();    
            sPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            sPort.addEventListener(e-> {this.postitSerialEventHandler(e);});
        
        }
        catch(SerialPortException e)
        {
            System.out.println(e.getMethodName()+" : "+ e.getExceptionType());
        } 
    }
    
    
    /**
     * Cette methode repond à l'envoie de donnée sur le port serie par le
     * hardware. Elle à en charge de recupérer les données et de les 
     * analyser une fois une trame trasmise
     * 
     * @param evt 
     */
    private void postitSerialEventHandler(SerialPortEvent evt)
    {
        try 
        {
            if (evt.isRXCHAR())
            {
                hardwareMessage.append(sPort.readString()); 
            }
        
        }
        catch(SerialPortException e)
        {
            System.out.println(e.getMethodName()+" : "+ e.getExceptionType());
        } 
    }
    
    private void analyseHardwareMessage()
    {
        
    }
    
}

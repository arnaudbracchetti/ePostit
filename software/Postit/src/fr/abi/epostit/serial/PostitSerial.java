/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.abi.epostit.serial;

import java.util.function.Consumer;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortList;

/**
 * Classe de gestion de la liaison serie avec la partie hardware de l'ePostit
 *
 * @author abracchetti
 */
public class PostitSerial
{

    private SerialPort sPort;
    private SerialMessage message = new SerialMessage();
    private Consumer<InternalKanban> newKanbanCallback;

    public PostitSerial(Consumer<InternalKanban> callback)
    {
        sPort = null;
        newKanbanCallback = callback;
    }

    /**
     * Cette methode ouvre et paramètre le port. La methode "postitSerialHandler
     * est positionné comme listener des données reçues sur le port.
     *
     * Paramètres utilisées pour l'ouverture du port : - 9600 baud - 8 bits de
     * données - 2 bits de stop - pas de partité
     *
     * @throws jssc.SerialPortException
     */
    public void openPort(String portName)
    {
        try
        {
            closePort();

            sPort = new SerialPort(portName);
            sPort.openPort();
            sPort.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_2, SerialPort.PARITY_NONE);
            sPort.addEventListener((e) ->
            {
                this.postitSerialEventHandler(e);
            });

        }
        catch (SerialPortException e)
        {
            System.out.println(e.getMethodName() + " : " + e.getExceptionType());
        }
    }

    public void closePort()
    {
        try
        {
            if (sPort != null)
            {
                if (sPort.isOpened())
                {
                    sPort.closePort();
                }
            }

            sPort = null;
        }
        catch (Exception e)
        {
            //TODO gere l'excption
        }

    }

    public static String[] getPorts()
    {
        return SerialPortList.getPortNames();
    }

    /**
     * Cette methode repond à l'envoie de donnée sur le port serie par le
     * hardware. Elle à en charge de recupérer les données et de les analyser
     * une fois une trame trasmise.
     *
     * @param evt
     */
    public void postitSerialEventHandler(SerialPortEvent evt)
    {
        try
        {
            if (evt.isRXCHAR())
            {
                if (message.buildMessage(sPort.readString()))
                {
                    newKanbanCallback.accept(message.getKanban());
                }
            }

        }
        catch (SerialPortException e)
        {
            System.out.println(e.getMethodName() + " : " + e.getExceptionType());
        }
    }

}

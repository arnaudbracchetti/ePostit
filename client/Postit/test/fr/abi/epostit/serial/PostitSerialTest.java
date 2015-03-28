/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.abi.epostit.serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import mockit.Expectations;
import mockit.Mocked;
import static org.hamcrest.Matchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author abracchetti
 */
public class PostitSerialTest {
    
    public PostitSerialTest() {
    }
    

    boolean toTest = false;
    @Test
    public void testEventHandler(@Mocked final SerialPortEvent serialPortEventMocked,
                                 @Mocked final SerialPort serialPortMocked) throws SerialPortException {
        
        new Expectations() {{
           serialPortEventMocked.isRXCHAR(); result = true;
           serialPortMocked.readString(); result = "<1:1,3,5;2:2,4,6>";
           serialPortMocked.openPort();
        }};
        
        
        toTest = false;
        PostitSerial instance = new PostitSerial("COM4", (kanban)->{toTest = true;});
        instance.postitSerialEventHandler(serialPortEventMocked);
        
        assertThat(toTest, is(true));
    }
    
}

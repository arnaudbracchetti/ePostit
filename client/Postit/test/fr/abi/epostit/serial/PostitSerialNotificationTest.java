/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.abi.epostit.serial;

import java.util.Map;
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
public class PostitSerialNotificationTest {
    
    public PostitSerialNotificationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of notifyPostit method, of class PostitSerialNotification.
     */
    @Test
    public void testNotifyPostit() {
        System.out.println("notifyPostit");
        Map<Integer, Integer> kanban = null;
        PostitSerialNotification instance = new PostitSerialNotificationImpl();
        instance.notifyPostit(kanban);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class PostitSerialNotificationImpl implements PostitSerialNotification {

        public void notifyPostit(Map<Integer, Integer> kanban) {
        }
    }

    
    
}

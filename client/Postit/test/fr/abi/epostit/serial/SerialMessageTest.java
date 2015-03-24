/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.abi.epostit.serial;

import static org.hamcrest.Matchers.isIn;

import java.util.List;
import java.util.Set;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.hamcrest.collection.IsIn;

/**
 *
 * @author abracchetti
 */
public class SerialMessageTest {
    
    public SerialMessageTest() {
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
     * Test of buildMessage method, of class SerialMessage.
     */
    
    
    @Test
    public void testLeMessageconstritParEtapeEtEstComplet() {
        System.out.println("testLeMessagenEstPasComplet");
        
        SerialMessage instance = new SerialMessage();
        boolean result = instance.buildMessage("<1:");        
        assertFalse("Le message en cours de construction est donné comme complet alors que ce n'est pas le cas", 
                    result);
        assertEquals("Erreur dans la zone de construction des messages", 
                     "<1:", instance.getBuidingZone() );
        
        result = instance.buildMessage("2,3;");        
        assertFalse("Le message en cours de construction est donné comme complet alors que ce n'est pas le cas", result);
        assertEquals("Erreur dans la zone de construction des messages", 
                     "<1:2,3;", instance.getBuidingZone() );
        
        result = instance.buildMessage("2:1><1");        
        assertTrue("Le message en cours de construction est donné comme incomplet alors que ce n'est pas le cas", result);
        assertEquals("Erreur dans la zone de construction des messages", 
                     "<1", instance.getBuidingZone() );
    }
    
    @Test
    public void testInterpretationDuMessage_NomDesColonnes()
    {
        System.out.println("testInterpretationDuMessage_NomDesColonnes");
        
        // init
        SerialMessage instance = new SerialMessage();
        
        // run
        instance.buildMessage("<1:2,3;3:1,4;:4:5>");
        Set<String> col = instance.getColumnsName();
        
        assertTrue("La colonne 1 existe", col.contains("1"));
        assertFalse("La colonne 2 n'existe pas", col.contains("2"));
        assertTrue("La colonne 3 existe", col.contains("3"));
        assertTrue("La colonne 4 existe", col.contains("4"));
        assertFalse("La colonne 5 n'existe pas", col.contains("5"));
        
    }

    @Test
    public void testInterpretationDuMessage_ContenuDesColonnes()
    {
        System.out.println("testInterpretationDuMessage_ContenuDesColonnes");
        
        // init
        SerialMessage instance = new SerialMessage();
        
        // run
        instance.buildMessage("<1:2,3;3:1,4;:4:5>");
        Set<String> col = instance.getColumnsName();
        
        for(String colName : col)
        {
            List<Integer> colContent = instance.getColumnContent(colName);
            
            if (colName.equals("1"))
            {
                assertEquals("La colonne 1 contient 2 élelments", 2, colContent.size());
                assertThat("La colonne 1 contient le postit 2", 2, isIn(colContent));
                assertThat("La colonne 1 contient le postit 3", 3, isIn(colContent));
            }
            else if(colName.equals("3")) 
            {
                assertEquals("La colonne 3 contient 2 élelments", 2, colContent.size());
                assertThat("La colonne 3 contient le postit 1", 1, isIn(colContent));
                assertThat("La colonne 3 contient le postit 4", 4, isIn(colContent));
            }
            else if(colName.equals("4")) 
            {
                assertEquals("La colonne 4 contient 1 élelments", 1, colContent.size());
                assertThat("La colonne 4 contient le postit 5", 5, isIn(colContent));
            }
            
            
        }
        
        
    }
    
}

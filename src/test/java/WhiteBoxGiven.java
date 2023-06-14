package test.java;

import main.java.Alcohol;
import main.java.Cart;
import main.java.Dairy;
import main.java.FrozenFood;
import main.java.Produce;
import main.java.UnderAgeException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WhiteBoxGiven {

    Cart cart;

    @Before
    public void setUp() throws Exception {
        cart = new Cart(45);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTax() {
        assertEquals(4.0, cart.getTax(50, "AZ"), .01);
    }

    @Test
    public void nodeAndEdgeCoverage1() {
    	//cart = new Cart(45);
    	
    	cart.addItem(new Produce());
    	cart.addItem(new Produce());
    	cart.addItem(new Produce());
    	cart.addItem(new Alcohol());
    	cart.addItem(new FrozenFood());
    	cart.addItem(new Dairy());
    	
    	try {
			assertEquals(4.0, cart.Amount_saved(), .01);
		} catch (UnderAgeException e) {
			fail("User is underage, but this isn't the place to test that");
			e.printStackTrace();
		}
    }
    
    @Test
    public void nodeAndEdgeCoverage2() {
    	cart = new Cart(20);
    	
    	cart.addItem(new Alcohol());
    	
    	try {
			cart.Amount_saved();
			fail("Should have thrown UnderAgeException");
		} catch (UnderAgeException e) {
			assertEquals("The User is not of age to purchase alcohol!", e.getMessage());
			// e.printStackTrace();
		}
    }
    
    @Test
    public void edgeCoverage3() {
    	cart = new Cart(45);
    	
    	cart.addItem(new Produce());
    	
    	try {
			assertEquals(0.0, cart.Amount_saved(), .01);
		} catch (UnderAgeException e) {
			fail("User is underage, but this isn't the place to test that");
			e.printStackTrace();
		}
    }
}
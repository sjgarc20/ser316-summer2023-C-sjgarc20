package test.java;

import main.java.Alcohol;
import main.java.Cart;
import main.java.Dairy;
import main.java.FrozenFood;
import main.java.Produce;
import main.java.Product;
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

    /**
     * Tests all nodes except the node throwing UnderAgeException
     * Tests all edges except edge to UnderAgeException and edge
     * from last else if being false
     */
    @Test
    public void nodeAndEdgeCoverage1() {
        cart = new Cart(45);

        cart.addItem(new Produce());
        cart.addItem(new Produce());
        cart.addItem(new Produce());
        cart.addItem(new Alcohol());
        cart.addItem(new FrozenFood());
        cart.addItem(new Dairy());

        try {
            assertEquals(4.0, cart.amountSaved(), .01);
        } catch (UnderAgeException e) {
            fail("User is underage, but this isn't the place to test that");
            e.printStackTrace();
        }
    }

    /**
     * Tests node for throwing UnderAgeException
     * Tests edge to UnderAgeException
     */
    @Test
    public void nodeAndEdgeCoverage2() {
        cart = new Cart(20);

        cart.addItem(new Alcohol());

        try {
            cart.amountSaved();
            fail("Should have thrown UnderAgeException");
        } catch (UnderAgeException e) {
            assertEquals("The User is not of age to purchase alcohol!", e.getMessage());
        }
    }

    /**
     * Tests edge from final else if resolving as false
     */
    @Test
    public void edgeCoverage3() {
        cart = new Cart(45);

        cart.addItem(new Product(10));

        try {
            assertEquals(0.0, cart.amountSaved(), .01);
        } catch (UnderAgeException e) {
            fail("User is underage, but this isn't the place to test that");
            e.printStackTrace();
        }
    }

    /**
     * Test calcCost all nodes except that which throws UnderAgeException
     */
    @Test
    public void nodeAndEdgeCoverageCalcCost1() {
        cart = new Cart(45);

        cart.addItem(new Produce());
        cart.addItem(new Produce());
        cart.addItem(new Produce());
        cart.addItem(new Alcohol());
        cart.addItem(new FrozenFood());

        try {
            assertEquals(16.2, cart.calcCost(), .01);
        } catch (UnderAgeException e) {
            fail("User is underage, but this isn't the place to test that");
            e.printStackTrace();
        }
    }

    /**
     * Test calcCost node that throws UnderAgeException
     */
    @Test
    public void nodeAndEdgeCoverageCalcCost2() {
        cart = new Cart(20);

        cart.addItem(new Alcohol());

        try {
            cart.calcCost();
            fail("Should have thrown UnderAgeException");
        } catch (UnderAgeException e) {
            assertEquals("The User is not of age to purchase alcohol!", e.getMessage());
        }
    }

    /**
     * Test all nodes of getTax
     */
    @Test
    public void nodeCoverageGetTax() {
        cart = new Cart(45);

        assertEquals(0.08, cart.getTax(1.0, "AZ"), .01);
        assertEquals(0.09, cart.getTax(1.0, "CA"), .01);
        assertEquals(0.10, cart.getTax(1.0, "NY"), .01);
        assertEquals(0.07, cart.getTax(1.0, "CO"), .01);
    }

    /**
     * Test nodes of removeItem
     */
    @Test
    public void removeItemNodeCoverage() {
        cart = new Cart(45);

        cart.addItem(new Alcohol());
        cart.addItem(new Alcohol());
        cart.removeItem(new Alcohol());

        try {
            assertEquals(8.64, cart.calcCost(), .01);
        } catch (UnderAgeException e) {
            fail("User is underage, but this isn't the place to test that");
            e.printStackTrace();
        }
    }
}
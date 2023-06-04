package test.java;

import main.java.Cart;
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

}
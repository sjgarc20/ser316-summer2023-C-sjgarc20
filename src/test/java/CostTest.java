/**
 * @file costTest.java
 * @author Sam
 * @date 06/12/2023
 * 
 * @description For running tests of my own implementation of calcCost()
 */

package test.java;

import main.java.*;
import org.junit.Test;

import static org.junit.Assert.*;



public class CostTest {


    private Cart createCart(int age) throws Exception {
        return new Cart(age);
    }

    // A sample Cart

    Cart cart1;
    double cart1Expected;


    @org.junit.Before
    public void setUp() throws Exception {

        // all carts should be set up like this

        // cart created with an age 40 shopper
        cart1 = createCart(40);
        for (int i = 0; i < 2; i++) {
            cart1.addItem(new Alcohol());
        }
        for(int i = 0; i < 3; i++) {
            cart1.addItem(new Dairy());
        }
        for(int i = 0; i < 4; i++) {
            cart1.addItem(new Meat());
        }

        cart1Expected = 70.2;
    }

    // sample test
    @Test
    public void calcCostCart1() throws UnderAgeException {
        double amount = cart1.calcCost();
        assertEquals(cart1Expected, amount, .01);
    }




    @Test
    public void costOfDairyCorrect() throws Exception {
        Cart testCart = createCart(25);

        testCart.addItem(new Dairy());

        assertEquals(3.24, testCart.calcCost(), .01);
    }

    @Test
    public void costOfMeatCorrect() throws Exception {
        Cart testCart = createCart(25);

        testCart.addItem(new Meat());

        assertEquals(10.8, testCart.calcCost(), .01);
    }

    @Test
    public void costOfProduceCorrect() throws Exception {
        Cart testCart = createCart(25);

        testCart.addItem(new Produce());

        assertEquals(2.16, testCart.calcCost(), .01);

        testCart.addItem(new Produce());

        assertEquals(4.32, testCart.calcCost(), .01);
    }

    @Test
    public void costOfAlcoholCorrect() throws Exception {
        Cart testCart = createCart(25);

        testCart.addItem(new Alcohol());

        assertEquals(8.64, testCart.calcCost(), .01);
    }

    @Test
    public void costOfFrozenFoodCorrect() throws Exception {
        Cart testCart = createCart(25);

        testCart.addItem(new FrozenFood());

        assertEquals(5.4, testCart.calcCost(), .01);
    }

    @Test
    public void costOfAlcholAndFrozenFoodCorrect() throws Exception {
        Cart testCart = createCart(25);

        testCart.addItem(new Alcohol());
        testCart.addItem(new FrozenFood());

        assertEquals(10.8, testCart.calcCost(), .01);
    }

    @Test
    public void costOfCorrect() throws Exception {
        Cart testCart = createCart(25);

        testCart.addItem(new Dairy());

        assertEquals(3.24, testCart.calcCost(), .01);
    }

    @Test
    public void costOfProduce3for5Correct() throws Exception {
        Cart testCart = createCart(25);

        for (int i = 0; i < 3; i++) {
            testCart.addItem(new Produce());
        }

        assertEquals(5.4, testCart.calcCost(), .01);
    }

    @Test
    public void multiple3for5ProduceDeal() throws Exception {
        Cart testCart = createCart(25);

        for (int i = 0; i < 6; i++) {
            testCart.addItem(new Produce());
        }

        assertEquals(10.8, testCart.calcCost(), .01);
    }

    @Test
    public void multipleTypesOfDeals() throws Exception {
        Cart testCart = createCart(25);

        testCart.addItem(new Alcohol());
        testCart.addItem(new FrozenFood());
        testCart.addItem(new Alcohol());
        testCart.addItem(new FrozenFood());
        for (int i = 0; i < 6; i++) {
            testCart.addItem(new Produce());
        }

        assertEquals(32.4, testCart.calcCost(), .01);
    }

    @Test
    public void alcoholAgeLimitEnforced() throws Exception, UnderAgeException {
        Cart testCart = createCart(0);
        testCart.addItem(new Alcohol());
        try {
            testCart.calcCost();
            fail("Should have thrown UnderAgeException");
        } catch (UnderAgeException e) {
            assertEquals("The User is not of age to purchase alcohol!", e.getMessage());
        }

        testCart = createCart(1);
        testCart.addItem(new Alcohol());
        try {
            testCart.calcCost();
            fail("Should have thrown UnderAgeException");
        } catch (UnderAgeException e) {
            assertEquals("The User is not of age to purchase alcohol!", e.getMessage());
        }

        testCart = createCart(19);
        testCart.addItem(new Alcohol());
        try {
            testCart.calcCost();
            fail("Should have thrown UnderAgeException");
        } catch (UnderAgeException e) {
            assertEquals("The User is not of age to purchase alcohol!", e.getMessage());
        }

        testCart = createCart(20);
        testCart.addItem(new Alcohol());
        try {
            testCart.calcCost();
            fail("Should have thrown UnderAgeException");
        } catch (UnderAgeException e) {
            assertEquals("The User is not of age to purchase alcohol!", e.getMessage());
        }
    }

    @Test
    public void legalAgedShoppersAllowedAlcohol() throws Exception, UnderAgeException {
        Cart testCart = createCart(21);
        testCart.addItem(new Alcohol());
        assertNotEquals("Legal Age shopper was denied alcohol!", 0, testCart.calcCost(), .01);

        testCart = createCart(22);
        testCart.addItem(new Alcohol());
        assertNotEquals("Legal Age shopper was denied alcohol!", 0, testCart.calcCost(), .01);

        testCart = createCart(30);
        testCart.addItem(new Alcohol());
        assertNotEquals("Legal Age shopper was denied alcohol!", 0, testCart.calcCost(), .01);

        testCart = createCart(100);
        testCart.addItem(new Alcohol());
        assertNotEquals("Legal Age shopper was denied alcohol!", 0, testCart.calcCost(), .01);
    }
}
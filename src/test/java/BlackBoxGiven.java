package test.java;

import main.java.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;

import main.java.Cart;
import main.java.Cart1;
import main.java.Cart2;
import main.java.Cart3;
import main.java.Cart4;
import main.java.Cart5;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class BlackBoxGiven {

    private Class<Cart> classUnderTest;

    @SuppressWarnings("unchecked")
    public BlackBoxGiven(Object classUnderTest) {
        this.classUnderTest = (Class<Cart>) classUnderTest;
    }

    // Define all classes to be tested
    @Parameterized.Parameters
    public static Collection<Object[]> cartClassUnderTest() {
        Object[][] classes = {
                {Cart0.class},
                {Cart1.class},
                {Cart2.class},
                {Cart3.class},
                {Cart4.class},
                {Cart5.class}
        };
        return Arrays.asList(classes);
    }

    private Cart createCart(int age) throws Exception {
        Constructor<Cart> constructor = classUnderTest.getConstructor(Integer.TYPE);
        return constructor.newInstance(age);
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
        Cart test_cart = createCart(25);

        test_cart.addItem(new Dairy());

        assertEquals(3.24, test_cart.calcCost(), .01);
    }

    @Test
    public void costOfMeatCorrect() throws Exception {
        Cart test_cart = createCart(25);

        test_cart.addItem(new Meat());

        assertEquals(10.8, test_cart.calcCost(), .01);
    }

    @Test
    public void costOfProduceCorrect() throws Exception {
        Cart test_cart = createCart(25);

        test_cart.addItem(new Produce());

        assertEquals(2.16, test_cart.calcCost(), .01);

        test_cart.addItem(new Produce());

        assertEquals(4.32, test_cart.calcCost(), .01);
    }

    @Test
    public void costOfAlcoholCorrect() throws Exception {
        Cart test_cart = createCart(25);

        test_cart.addItem(new Alcohol());

        assertEquals(8.64, test_cart.calcCost(), .01);
    }

    @Test
    public void costOfFrozenFoodCorrect() throws Exception {
        Cart test_cart = createCart(25);

        test_cart.addItem(new FrozenFood());

        assertEquals(5.4, test_cart.calcCost(), .01);
    }

    @Test
    public void costOfAlcholAndFrozenFoodCorrect() throws Exception {
        Cart test_cart = createCart(25);

        test_cart.addItem(new Alcohol());
        test_cart.addItem(new FrozenFood());

        assertEquals(10.8, test_cart.calcCost(), .01);
    }

    @Test
    public void costOfCorrect() throws Exception {
        Cart test_cart = createCart(25);

        test_cart.addItem(new Dairy());

        assertEquals(3.24, test_cart.calcCost(), .01);
    }

    @Test
    public void costOfProduce3for5Correct() throws Exception {
        Cart test_cart = createCart(25);

        for(int i = 0; i < 3; i++) {
            test_cart.addItem(new Produce());
        }

        assertEquals(5.4, test_cart.calcCost(), .01);
    }

    @Test
    public void multiple3for5ProduceDeal() throws Exception {
        Cart test_cart = createCart(25);

        for(int i = 0; i < 6; i++) {
            test_cart.addItem(new Produce());
        }

        assertEquals(10.8, test_cart.calcCost(), .01);
    }

    @Test
    public void multipleTypesOfDeals() throws Exception {
        Cart test_cart = createCart(25);

        test_cart.addItem(new Alcohol());
        test_cart.addItem(new FrozenFood());
        test_cart.addItem(new Alcohol());
        test_cart.addItem(new FrozenFood());
        for(int i = 0; i < 6; i++) {
            test_cart.addItem(new Produce());
        }

        assertEquals(32.4, test_cart.calcCost(), .01);
    }

    @Test
    public void alcoholAgeLimitEnforced() throws Exception, UnderAgeException {
        Cart test_cart = createCart(0);
        test_cart.addItem(new Alcohol());
        assertNotEquals("Under Aged shopper was allowed alcohol!", 8.64, test_cart.calcCost(), .01);

        test_cart = createCart(1);
        test_cart.addItem(new Alcohol());
        assertNotEquals("Under Aged shopper was allowed alcohol!", 8.64, test_cart.calcCost(), .01);

        test_cart = createCart(19);
        test_cart.addItem(new Alcohol());
        assertNotEquals("Under Aged shopper was allowed alcohol!", 8.64, test_cart.calcCost(), .01);

        test_cart = createCart(20);
        test_cart.addItem(new Alcohol());
        assertNotEquals("Under Aged shopper was allowed alcohol!", 8.64, test_cart.calcCost(), .01);
    }

    @Test
    public void legalAgedShoppersAllowedAlcohol() throws Exception, UnderAgeException {
        Cart test_cart = createCart(21);
        test_cart.addItem(new Alcohol());
        assertNotEquals("Legal Age shopper was denied alcohol!", 0, test_cart.calcCost(), .01);

        test_cart = createCart(22);
        test_cart.addItem(new Alcohol());
        assertNotEquals("Legal Age shopper was denied alcohol!", 0, test_cart.calcCost(), .01);

        test_cart = createCart(30);
        test_cart.addItem(new Alcohol());
        assertNotEquals("Legal Age shopper was denied alcohol!", 0, test_cart.calcCost(), .01);

        test_cart = createCart(100);
        test_cart.addItem(new Alcohol());
        assertNotEquals("Legal Age shopper was denied alcohol!", 0, test_cart.calcCost(), .01);
    }
    //    @Test
    //    public void noFalseUnderAgeCatches() throws Exception {
    //    	Cart test_cart = createCart(0);
    //        try { 
    //        	test_cart.addItem(new Dairy());
    //        } catch (Exception e) {
    //        	assertEquals("If Failed then False Alcohol Flag!", 3.24, test_cart.calcCost(), .01);
    //        }
    //        
    //        test_cart = createCart(1);
    //        try {
    //        	test_cart.addItem(new Dairy());
    //        } catch (Exception e) {
    //        	assertEquals("If Failed then False Alcohol Flag!", 3.24, test_cart.calcCost(), .01);
    //        
    //        test_cart = createCart(19);
    //        	test_cart.addItem(new Dairy());
    //        	assertEquals("If Failed then False Alcohol Flag!", 3.24, test_cart.calcCost(), .01);
    //        
    //        test_cart = createCart(20);
    //        	test_cart.addItem(new Dairy());
    //        	assertEquals("If Failed then False Alcohol Flag!", 3.24, test_cart.calcCost(), .01);
    //        	
    //        test_cart = createCart(21);
    //        	test_cart.addItem(new Dairy());
    //        	assertEquals("If Failed then False Under Age Catch!", 0, test_cart.calcCost(), .01);
    //        
    //        test_cart = createCart(22);
    //        test_cart.addItem(new Dairy());
    //        	assertEquals("If Failed then False Under Age Catch!", 0, test_cart.calcCost(), .01);
    //        
    //        test_cart = createCart(30);
    //       	test_cart.addItem(new Dairy());
    //        assertEquals("If Failed then False Under Age Catch!", 0, test_cart.calcCost(), .01);
    //        
    //        test_cart = createCart(100);
    //        test_cart.addItem(new Dairy());
    //        assertEquals("If Failed then False Under Age Catch!", 0, test_cart.calcCost(), .01);
    //    }
}
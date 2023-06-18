/**
 * @file Main.java
 * @author Sam
 * @date 6/12/2023
 * 
 * @description Entry point of program
 */

package main.java;

public class Main {
    /**
     * Entry point of program.
     * @param args program arguments
     * @throws UnderAgeException throws exception if user is under 21
     */
    public static void main(String[] args) throws UnderAgeException {

        // Instantiating some products
        Produce apple = new Produce();
        Produce orange = new Produce();
        Produce banana = new Produce();
        Produce beet = new Produce();

        Alcohol budlight = new Alcohol();
        Alcohol coorslight = new Alcohol();

        FrozenFood pizza = new FrozenFood();

        Meat chicken = new Meat();

        Dairy milk = new Dairy();

        Cart cart = new Cart(25);
        cart.addItem(apple);
        cart.addItem(orange);
        cart.addItem(banana);
        cart.addItem(beet);
        cart.addItem(budlight);
        cart.addItem(coorslight);
        cart.addItem(pizza);
        cart.addItem(chicken);
        cart.addItem(milk);

        System.out.println(cart.calcCost());
        System.out.println(cart.amountSaved());
    }
}

/**
 * @file Cart.java
 * @author Sam
 * @date 6/12/2023
 * 
 * @description For calculating the cost of items in the cart,
 * including factoring in deals/discounts and tax
 */

package main.java;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static final int LEGAL_AGE = 21;

    private int userAge;
    private List<Product> cart;
    // public int cartStorage; // SER316 TASK 2 SPOTBUGS FIX

    /**
     * Calculates the final cost after all savings and tax has been applied. Also checks
     * that the user is of age to purchase alcohol if it is in their cart at checkout. 
     * Sales tax is always AZ tax.
     *
     * <p>
     * Calculation is based off of the following prices and deals:
     * Dairy -> $3
     * Meat -> $10
     * Produce -> $2 or 3 for $5
     * Alcohol -> $8
     * Frozen Food -> $5
     * Alcohol + Frozen Food -> $10
     * </p>
     * If there is an alcohol product in the cart and the user is under 21, then an
     * UnderAgeException should be thrown.
     *
     * @return double totalCost
     * @throws UnderAgeException thrown if user is under 21
     */
    public double calcCost() throws UnderAgeException {
        double totalCost = 0.0;
        int alcoholCounter = 0;
        int frozenFoodCounter = 0;
        int produceCounter = 0;

        for (int i = 0; i < this.cart.size(); i++) {
            totalCost += (double)((Product)this.cart.get(i)).getCost();
            if (((Product)this.cart.get(i)).getClass() == Alcohol.class) {
                ++alcoholCounter;
                if (this.userAge < LEGAL_AGE) {
                    throw new UnderAgeException("The User is not of age to purchase alcohol!");
                }
            } else if (((Product)this.cart.get(i)).getClass() == FrozenFood.class) {
                ++frozenFoodCounter;
            } else if (((Product)this.cart.get(i)).getClass() == Produce.class) {
                ++produceCounter;
            }

            if (alcoholCounter >= 1 && frozenFoodCounter >= 1) {
                totalCost -= 3.0;
                --alcoholCounter;
                --frozenFoodCounter;
            }

            if (produceCounter >= 3) {
                --totalCost;
                produceCounter -= 3;
            }
        }

        return totalCost + this.getTax(totalCost, "AZ");
    }

    /**
     * calculates how much was saved in the current shopping cart based on the deals.
     * @return saved amount
     * @throws UnderAgeException throws exception if alcohol is bought from underage person
     */
    public int amountSaved() throws UnderAgeException {
        int amountSaved = 0;
        if (getTotalAlcohol() > 0 && this.userAge < LEGAL_AGE) {
            throw new UnderAgeException("The User is not of age to purchase alcohol!");
        }
        amountSaved += getAlcoholFrozenFoodSavings();
        amountSaved += getProduceSavings();
        return amountSaved;
    }
    
    private int getAlcoholFrozenFoodSavings() {
        int savings = 0;
        int totalAlcohol = getTotalAlcohol();
        int totalFrozenFood = getTotalFrozenFood();
        while (totalAlcohol > 0 && totalFrozenFood > 0) {
            savings += 3;
            totalAlcohol--;
            totalFrozenFood--;
        }
        return savings;
    }
    
    private int getProduceSavings() {
        int savings = 0;
        int totalProduce = getTotalProduce();
        while (totalProduce >= 3) {
            savings++;
            totalProduce -= 3;
        }
        return savings;
    }
    
    private int getTotalAlcohol() {
        int totalAlcohol = 0;
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getClass().toString().equals(Alcohol.class.toString())) {
                totalAlcohol++;
            }
        }
        return totalAlcohol;
    }
    
    private int getTotalFrozenFood() {
        int totalFrozenFood = 0;
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getClass().toString().equals(FrozenFood.class.toString())) {
                totalFrozenFood++;
            }
        }
        return totalFrozenFood;
    }

    private int getTotalProduce() {
        int totalProduce = 0;
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getClass().toString().equals(Produce.class.toString())) {
                totalProduce++;
            }
        }
        return totalProduce;
    }

    /**
     * Gets the tax based on state and the total.
     * @param totalBeforeTax Total cost of cart before tax
     * @param twoLetterStateAbbreviation US state abbreviation
     * @return newTotal total cost of cart after tax
     */
    public double getTax(double totalBeforeTax, String twoLetterStateAbbreviation) {
        double newTotal = 0;
        switch (twoLetterStateAbbreviation) {
            case "AZ":
                newTotal = totalBeforeTax * .08;
                break;
            case "CA":
                newTotal = totalBeforeTax * .09;
                break;
            case "NY":
                newTotal = totalBeforeTax * .1;
                break;
            case "CO":
                newTotal = totalBeforeTax * .07;
                break;
            default:
                return totalBeforeTax;
        }
        return newTotal;
    }

    public void addItem(Product np) {
        cart.add(np);
    }

    /**
     * Removes at item from user's cart.
     * @param productToRemove item to be removed from cart
     * @return true if found and removed, false if not found/removed
     */
    public boolean removeItem(Product productToRemove) { // SER316 TASK 2 SPOTBUGS FIX
        boolean test = false;
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getClass().equals(productToRemove.getClass())) {
                cart.remove(i);
                test = true;
                return test;
            }
        }
        return false;
    }

    public Cart(int age) {
        userAge = age;
        cart = new ArrayList<Product>();
    }
}

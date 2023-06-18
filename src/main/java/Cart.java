package main.java;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    protected int userAge;
    public List<Product> cart;
    public int cartStorage;

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
                if (this.userAge < 21) {
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
        int subTotal = 0;
        int costAfterSavings = 0;

        double produceCounter = 0;
        int alcoholCounter = 0;
        int frozenFoodCounter = 0;
        int dairyCounter = 0;

        for (int i = 0; i < cart.size(); i++) {
            subTotal += cart.get(i).getCost();
            costAfterSavings = costAfterSavings + cart.get(i).getCost();
            // changed all if statements from == to .eqauls to properly compare classes
            if (cart.get(i).getClass().toString().equals(Produce.class.toString())) {
                produceCounter++;

                if (produceCounter >= 3) {
                    costAfterSavings -= 1;
                    produceCounter = 0;
                }
            } else if (cart.get(i).getClass().toString().equals(Alcohol.class.toString())) {
                alcoholCounter++;
                if (userAge < 21) {
                    throw new UnderAgeException("The User is not of age to purchase alcohol!");
                }
            } else if (cart.get(i).getClass().toString().equals(FrozenFood.class.toString())) {
                frozenFoodCounter++;
            } else if (cart.get(i).getClass().toString().equals(Dairy.class.toString())) {
                // changed Dairy from FrozenFood because we already checked FrozenFood
                dairyCounter++;
            }
            if (alcoholCounter >= 1 && frozenFoodCounter >= 1) {
                costAfterSavings = costAfterSavings - 3; 
                // changed plus to minus, because discounts should be removed from cost
                alcoholCounter--;
                frozenFoodCounter--;
            }
        }

        return subTotal - costAfterSavings;
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
    public boolean removeItem(Product productToRemove)
    {
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

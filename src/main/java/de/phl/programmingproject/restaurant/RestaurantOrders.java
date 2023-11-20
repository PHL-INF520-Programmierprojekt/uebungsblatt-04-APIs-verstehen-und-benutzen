package de.phl.programmingproject.restaurant;

import java.util.Arrays;
import java.util.List;

/**
 * This class represents the main class of the restaurant exercise.
 */

public class RestaurantOrders {

    public static void main(final String[] args) {
        final List<String> orderNames = Arrays.asList(
                "Pizza", "Pasta", "Salad", "Soup", "Burger", "Steak", "Fries", "Ice Cream", "Cake", "Pie");
        // TODO: Implement this operation

        Employee owner = new Employee();
        Restaurant restaurant = new Restaurant(owner);

        Employee employee = new Employee();
        restaurant.hireEmployee(employee);

        for (String orderName : orderNames) {
            restaurant.placeOrder(new Order(orderName));
        }

        restaurant.process();
    }

}

package de.phl.programmingproject.restaurant;

import de.phl.programmingproject.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockedConstruction;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the {@link RestaurantOrders} exercise.
 */

public class RestaurantTest {

    Employee employeeSpy;


    Restaurant restaurantSpy;

    @BeforeEach
    public void initMocks() {
        employeeSpy = Mockito.spy(new Employee());
        restaurantSpy = Mockito.spy(new Restaurant(employeeSpy));
    }

    @Test
    public void task_1_restaurant_was_created() {
        List<List<?>> calls = new ArrayList<>();
        try (MockedConstruction<Restaurant> construction = TestUtils.observeConstruction(Restaurant.class,
                (mock, context) -> calls.add(new ArrayList<>(context.arguments())))) {
            RestaurantOrders.main(new String[0]);
            assertEquals(1, calls.size(), "Erstellen Sie genau ein Restaurant.");
            assertInstanceOf(Employee.class, calls.getFirst().getFirst(), "Das Restaurant benötigt einen Inhaber.");
        }
    }

    @Test
    public void task_1_restaurant_has_one_employee() {
        try (MockedConstruction<Restaurant> construction = TestUtils.observeConstruction(Restaurant.class)) {
            RestaurantOrders.main(new String[0]);
            assertEquals(1, construction.constructed().size(), "Erstellen Sie genau ein Restaurant.");
            verify(construction.constructed().getFirst()).hireEmployee(any(Employee.class));
        }
    }

    @Test
    public void task_1_ten_orders_placed() {
        try (MockedConstruction<Restaurant> construction = TestUtils.observeConstruction(Restaurant.class)) {
            RestaurantOrders.main(new String[0]);
            assertEquals(1, construction.constructed().size(), "Erstellen Sie genau ein Restaurant.");
            verify(construction.constructed().getFirst(), times(10)).placeOrder(any(Order.class));
            verify(construction.constructed().getFirst()).process();
        }
    }


    @Test
    public void task_2_placeOrder_with_null_order_throws_exception() throws Exception {
        assertThrows(Exception.class, () -> restaurantSpy.placeOrder(null),
                "The 'placeOrder' method of the 'Restaurant' class does not throw an exception when the given order is null.");
    }

    @Test
    public void task_2_placeOrder_calls_assignOrder_on_least_busy_employee() {
        Employee leastBusyEmployeeMock = Mockito.mock(Employee.class);
        when(leastBusyEmployeeMock.currentOrdersCount()).thenReturn(1);
        Employee busyEmployeeMock = Mockito.mock(Employee.class);
        when(busyEmployeeMock.currentOrdersCount()).thenReturn(2);

        when(employeeSpy.currentOrdersCount()).thenReturn(5);
        List<Employee> busyEmployees = new ArrayList<Employee>() {{
            add(busyEmployeeMock);
            add(employeeSpy);
        }};

        restaurantSpy.hireEmployee(leastBusyEmployeeMock);
        restaurantSpy.hireEmployee(busyEmployeeMock);

        restaurantSpy.placeOrder(new Order("test"));
        try {
            Mockito.verify(leastBusyEmployeeMock, Mockito.times(1)).assignOrder(Mockito.any(Order.class));
        } catch (AssertionError e) {
            fail("The 'placeOrder' method of the 'Restaurant' class does not call the 'assignOrder' method of the least busy employee.");
        }
        for (Employee employee : busyEmployees) {
            Mockito.verify(employee, Mockito.atLeast(1)).currentOrdersCount();
            Mockito.verify(employee, Mockito.never()).assignOrder(Mockito.any(Order.class));
        }
    }

    @Test
    public void task_2_assignOrder_with_null_order_throws_exception() {
        Employee employee = new Employee();
        assertThrows(Exception.class, () -> employee.assignOrder(null),
                "The 'assignOrder' method of the 'Employee' class does not throw an exception when the given order is null.");
    }

    @Test
    public void task_2_assignOrder_increases_order_count() {
        Employee employee = new Employee();
        employee.assignOrder(new Order("test"));
        assertEquals(1, employee.currentOrdersCount(), "The 'assignOrder' method of the 'Employee' class does not add the order to the queue of orders!");
    }

    @Test
    public void task_3_processOrder_calls_handle_and_removes_order_from_queue() {
        Employee employeeSpy = Mockito.spy(new Employee());
        Order orderSpy = Mockito.spy(new Order("spy"));
        employeeSpy.assignOrder(orderSpy);
        int orderCnt = employeeSpy.currentOrdersCount();
        employeeSpy.processOrders();
        try {
            Mockito.verify(orderSpy).handle();
        } catch (AssertionError e) {
            fail("The 'processOrder' method of the 'Employee' class does not call the 'handle' method of the 'Order' class");
        }
        assertEquals(orderCnt - 1, employeeSpy.currentOrdersCount(),
                "The 'processOrders' method of the 'Employee' class does not remove the order from the queue of orders.");

    }

    @Test
    public void task_4_restaurant_orders_markdown_file_exists_in_root_directory() {
        assertTrue(TestUtils.fileExistsInRootOrSrcDirectory("restaurant_orders.md"), "The file 'restaurant_orders.md' does not exist in the root (or './src') directory of the project.");
    }
}
package de.phl.programmingproject.restaurant;

import de.phl.programmingproject.TestUtils;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Test class for the {@link RestaurantOrders} exercise.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest({RestaurantOrders.class, RestaurantTest.class})
public class RestaurantTest {

    Employee employeeSpy;


    Restaurant restaurantSpy;

    static String restaurantOrdersFileContent;

    @Before
    public void initMocks() {
        employeeSpy = Mockito.spy(new Employee());
        restaurantSpy = Mockito.spy(new Restaurant(employeeSpy));
    }

    @BeforeClass
    public static void readMainMethod() {
        String filePath = "./src/main/java/de/phl/programmingproject/restaurant/RestaurantOrders.java";
        restaurantOrdersFileContent = null;
        try {
            restaurantOrdersFileContent = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @org.junit.Test
    public void task_1_restaurant_was_created() throws Exception {
        /*Pattern pattern = Pattern.compile(".*=.*new Restaurant\\(.*\\);");
        boolean restaurantCreated = false;
        for (String line : restaurantOrdersFileContent.split("\n")) {
            if (pattern.matcher(line).find()) {
               restaurantCreated = true;
               break;
            }
        }
        if(!restaurantCreated){
            fail("The 'Restaurant' object is not created in the 'main' method of the 'RestaurantOrders' file.");
        }*/

        PowerMockito.whenNew(Restaurant.class).withAnyArguments().thenReturn(restaurantSpy);
        RestaurantOrders.main(null);
        try {
            PowerMockito.verifyNew(Restaurant.class).withArguments(Mockito.any(Employee.class));
        } catch (AssertionError e) {
            fail("The 'Restaurant' object is not created in the 'main' method of the 'RestaurantOrders' file.");
        }
    }

    @Test
    public void task_1_restaurant_has_one_employee() throws Exception {
        /*
        Pattern pattern = Pattern.compile("\\.hireEmployee\\(.*\\);");
        for (String line : restaurantOrdersFileContent.split("\n")) {
            if(pattern.matcher(line).find())
                return;
        }
        fail("The 'Restaurant' has no employees! Please hire at least one employee.");
         */

        PowerMockito.whenNew(Restaurant.class).withAnyArguments().thenReturn(restaurantSpy);
        RestaurantOrders.main(null);
        try {
            Mockito.verify(restaurantSpy, Mockito.times(1)).hireEmployee(Mockito.any(Employee.class));
        } catch (AssertionError e) {
            fail("The 'Restaurant' has no employees! Please hire at least one employee.");
        }
    }

    @org.junit.Test
    public void task_1_ten_orders_placed() throws Exception {
        // mock the constructor of the Restaurant class
        PowerMockito.whenNew(Restaurant.class).withAnyArguments().thenReturn(restaurantSpy);
        RestaurantOrders.main(null);
        // verify with Mockito that the public 'placeOrder'  method was called 10 times
        try {
            Mockito.verify(restaurantSpy, Mockito.times(10)).placeOrder(Mockito.any(Order.class));
        } catch (AssertionError e) {
            fail("The 'placeOrder' method of the 'Restaurant' class was not called 10 times.");
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
        when(leastBusyEmployeeMock.currentOrderCount()).thenReturn(1);
        Employee busyEmployeeMock = Mockito.mock(Employee.class);
        when(busyEmployeeMock.currentOrderCount()).thenReturn(2);

        when(employeeSpy.currentOrderCount()).thenReturn(5);
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
            Mockito.verify(employee, Mockito.atLeast(1)).currentOrderCount();
            Mockito.verifyNoMoreInteractions(employee);
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
        assertEquals(1, employee.currentOrderCount(), "The 'assignOrder' method of the 'Employee' class does not add the order to the queue of orders!");
    }

    @Test
    public void task_3_processOrder_calls_handle_and_removes_order_from_queue() {
        Employee employeeSpy = Mockito.spy(new Employee());
        Order orderSpy = Mockito.spy(new Order("spy"));
        employeeSpy.assignOrder(orderSpy);
        int orderCnt = employeeSpy.currentOrderCount();
        employeeSpy.processOrders();
        try {
            Mockito.verify(orderSpy).handle();
        } catch (AssertionError e) {
            fail("The 'processOrder' method of the 'Employee' class does not call the 'handle' method of the 'Order' class");
        }
        assertEquals(orderCnt - 1, employeeSpy.currentOrderCount(),
                "The 'processOrders' method of the 'Employee' class does not remove the order from the queue of orders.");

    }

    @Test
    public void task_4_restaurant_orders_markdown_file_exists_in_root_directory() {
        assertTrue(TestUtils.fileExistsInRootOrSrcDirectory("restaurant_orders.md"), "The file 'restaurant_orders.md' does not exist in the root (or './src') directory of the project.");
    }
}
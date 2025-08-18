import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Order;
import service.OrderService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresql");
        OrderService orderService = new OrderService(emf);

        executeCrudTests(orderService);
        executeStreamTests(orderService);
    }

    public static List<Order> buildOrdersList() {
        return Arrays.asList(
                new Order("Ali Rezaei", LocalDate.of(2025, 1, 10), 120.50),
                new Order("Maryam Mohammadi", LocalDate.of(2025, 2, 5), 75.99),
                new Order("Hossein Karimi", LocalDate.of(2025, 2, 20), 200.00),
                new Order("Fatemeh Ahmadi", LocalDate.of(2025, 3, 2), 49.75),
                new Order("Reza Ghasemi", LocalDate.of(2025, 3, 12), 340.20),
                new Order("Zahra Moradi", LocalDate.of(2025, 3, 18), 89.95),
                new Order("Sara Sharifi", LocalDate.of(2025, 4, 1), 150.00),
                new Order("Mohammad Ali Hasani", LocalDate.of(2025, 4, 5), 510.40),
                new Order("Negin Ghanbari", LocalDate.of(2025, 4, 10), 15.25),
                new Order("Kianoush Heidari", LocalDate.of(2025, 4, 15), 110.10),

                new Order("Ali Rezaei", LocalDate.of(2025, 1, 10), 125.50),
                new Order("Ali Rezaei", LocalDate.of(2025, 2, 5), 89.99),
                new Order("Ali Rezaei", LocalDate.of(2025, 3, 15), 210.00),

                new Order("Sara Sharifi", LocalDate.of(2025, 1, 12), 65.75),
                new Order("Sara Sharifi", LocalDate.of(2025, 4, 22), 155.40),

                new Order("Hossein Karimi", LocalDate.of(2025, 1, 18), 99.99),
                new Order("Hossein Karimi", LocalDate.of(2025, 2, 28), 340.25),

                new Order("Maryam Mohammadi", LocalDate.of(2025, 3, 3), 45.00),
                new Order("Maryam Mohammadi", LocalDate.of(2025, 5, 10), 75.20),

                new Order("Navid Farhadi", LocalDate.of(2025, 3, 25), 300.00)
        );
    }

    public static void executeCrudTests(OrderService orderService) {
        List<Order> orders = buildOrdersList();

        orders.forEach(orderService::createOrder);

        Order order1 = orders.stream().filter(o -> o.getId() == 1).findFirst().get();
        order1.setCustomerName("James Rezaei");
        orderService.updateOrder(order1);

        orderService.removeOrder(5L);

        System.out.println();
        System.out.println("-----------------------------");
        System.out.println("----- CRUD Test Results -----");
        System.out.println("-----------------------------");
        System.out.println();

        String customerName = "ali rezaei";
        List<Order> ordersByCustomerName = orderService.getOrdersByCustomerName(customerName);

        ordersByCustomerName.forEach(o -> {
            System.out.println(o);
            System.out.println();
            System.out.println("-----------------------------");
            System.out.println();
        });
    }

    public static void executeStreamTests(OrderService orderService) {
        System.out.println();
        System.out.println("--------------------------");
        System.out.println("----- Stream Results -----");
        System.out.println("--------------------------");
        System.out.println();

        System.out.println("Total sales amount: " + orderService.calculateTotalSalesAmount());

        System.out.println();
        System.out.println("--------------------------");
        System.out.println();

        Order mostExpensiveOrder = orderService.getMostExpensiveOrder();
        System.out.println(
                "Most expensive order details | Name: " +
                        mostExpensiveOrder.getCustomerName() +
                        ", Total amount: " +
                        mostExpensiveOrder.getTotalAmount()
        );

        System.out.println();
        System.out.println("--------------------------");
        System.out.println();

        List<Order> ordersWithHigherAmount = orderService.getOrdersByHigherAmount(100D);
        ordersWithHigherAmount.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .forEach(o -> System.out.println("Order ID: " + o.getId() + ", Order Date: " + o.getOrderDate()));

        System.out.println();
        System.out.println("--------------------------");
        System.out.println();

        Map<String, Double> totalSalesAmountPerMonth = orderService.getTotalSalesAmountPerMonth();
        totalSalesAmountPerMonth.forEach((k, v) -> System.out.println(k + ": " + v));

        System.out.println();
        System.out.println("--------------------------");
        System.out.println();
    }
}

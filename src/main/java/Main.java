import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Order;
import service.OrderService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("postgresql");

        OrderService orderService = new OrderService(emf);

        List<Order> orders = buildOrdersList();

        orders.forEach(orderService::createOrder);

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
                new Order("Kianoush Heidari", LocalDate.of(2025, 4, 15), 110.10)
        );
    }
}

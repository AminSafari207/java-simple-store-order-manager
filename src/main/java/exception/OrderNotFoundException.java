package exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long orderId) {
        super("Order with ID '" + orderId + "' has not been found.");
    }
}

package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import model.Order;
import repository.OrderRepository;
import repository.impl.OrderRepositoryImpl;
import utils.ValidationUtils;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class OrderService {
    private final EntityManagerFactory emf;

    public OrderService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Order createOrder(Order order) {
        validateOrder(order);

        return executeTransaction(em -> {
            OrderRepository repo = new OrderRepositoryImpl(em);
            repo.create(order);
            return order;
        });
    }

    public void updateOrder(Order order) {
        validateOrder(order);

        executeTransaction(em -> {
            OrderRepository repo = new OrderRepositoryImpl(em);
            repo.update(order);
        });
    }

    public void removeOrder(Long orderId) {
        ValidationUtils.validateId(orderId, "orderId");

        executeTransaction(em -> {
            OrderRepository repo = new OrderRepositoryImpl(em);
            repo.delete(orderId);
        });
    }

    public List<Order> getAllOrders() {
        return executeTransaction(em -> {
            OrderRepository repo = new OrderRepositoryImpl(em);
            return repo.findAll();
        });
    }

    public void executeTransaction(Consumer<EntityManager> consumer) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            consumer.accept(em);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Transaction failed", e);
        } finally {
            em.close();
        }
    }

    public <T> T executeTransaction(Function<EntityManager, T> function) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            T res = function.apply(em);
            tx.commit();

            return res;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Transaction failed", e);
        } finally {
            em.close();
        }
    }

    public void validateOrder(Order order) {
        ValidationUtils.validateNotNull(order, "order");
        ValidationUtils.validateId(order.getId(), "order.getId()");
        ValidationUtils.validateString(order.getCustomerName(), 3, "order.getCustomerName()");
        ValidationUtils.validateNotNull(order.getOrderDate(), "order.getOrderDate()");
        validateTotalAmount(order.getTotalAmount(), "order.getTotalAmount()");
    }

    public void validateTotalAmount(Double totalAmount, String logName) {
        ValidationUtils.validateNotNull(totalAmount, logName);
        if (totalAmount < 0) throw new IllegalArgumentException(logName + " must be 0 or positive.");
    }
}

package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import model.Order;
import repository.OrderRepository;
import repository.impl.OrderRepositoryImpl;

import java.util.function.Consumer;
import java.util.function.Function;

public class OrderService {
    private final EntityManagerFactory emf;

    public OrderService(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void createOrder(Order order) {
        executeTransaction(em -> {
            OrderRepository repo = new OrderRepositoryImpl(em);
            repo.create(order);
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
}

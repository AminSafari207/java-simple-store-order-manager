package repository.impl;

import exception.RepositoryException;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import model.Order;
import repository.OrderRepository;

import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {
    private final EntityManager em;

    public OrderRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void create(Order order) {
        try {
            em.persist(order);
        } catch (PersistenceException e) {
            throw new RepositoryException("Persisting order has been failed.", e);
        }
    }

    @Override
    public List<Order> findAll() {
        String hqlQuery = "select o from Order o";

        try {
            return em.createQuery(hqlQuery, Order.class).getResultList();
        } catch (PersistenceException e) {
            throw new RepositoryException("Finding all orders has been failed.", e);
        }
    }

    @Override
    public Optional<Order> findById(Long id) {
        try {
            Order order = em.find(Order.class, id);
            return Optional.ofNullable(order);
        } catch (PersistenceException e) {
            throw new RepositoryException("Finding order by id has been failed.", e);
        }
    }

    @Override
    public List<Order> findByCustomerName(String customerName) {
        String hqlQuery = "select o from Order o where lower(o.customerName) like :customerName";

        try {
            return em.createQuery(hqlQuery, Order.class)
                    .setParameter("customerName", "%" + customerName + "%")
                    .getResultList();
        } catch (PersistenceException e) {
            throw new RepositoryException("Finding order by customer name has been failed.", e);
        }
    }

    @Override
    public void update(Order order) {
        try {
            em.merge(order);
        } catch (PersistenceException e) {
            throw new RepositoryException("Updating order has been failed.", e);
        }
    }

    @Override
    public void delete(Order order) {
        try {
            Order orderRef = em.getReference(Order.class, order.getId());
            em.remove(orderRef);
        } catch (EntityNotFoundException e) {
            throw new RepositoryException("Order not found.", e);
        } catch (PersistenceException e) {
            throw new RepositoryException("Deleting entity has been failed.", e);
        }
    }
}

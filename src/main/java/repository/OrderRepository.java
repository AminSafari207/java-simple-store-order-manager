package repository;

import model.Order;

import java.util.List;

public interface OrderRepository extends BaseRepository<Order, Long> {
    List<Order> findByCustomerName(String name);
}

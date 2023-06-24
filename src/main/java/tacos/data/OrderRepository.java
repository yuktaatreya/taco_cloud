package tacos.data;

import tacos.Order;

public interface OrderRepository {
    Order saveOrder(Order order);
}

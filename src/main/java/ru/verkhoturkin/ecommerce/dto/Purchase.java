package ru.verkhoturkin.ecommerce.dto;

import lombok.Data;
import ru.verkhoturkin.ecommerce.entity.Address;
import ru.verkhoturkin.ecommerce.entity.Customer;
import ru.verkhoturkin.ecommerce.entity.Order;
import ru.verkhoturkin.ecommerce.entity.OrderItem;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;
    private Address shippingAddress;
    private Address billingAddress;
    private Order order;
    private Set<OrderItem> orderItems;
}

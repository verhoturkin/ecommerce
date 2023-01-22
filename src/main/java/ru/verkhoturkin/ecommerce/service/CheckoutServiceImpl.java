package ru.verkhoturkin.ecommerce.service;

import org.springframework.stereotype.Service;
import ru.verkhoturkin.ecommerce.dao.CustomerRepository;
import ru.verkhoturkin.ecommerce.dto.Purchase;
import ru.verkhoturkin.ecommerce.dto.PurchaseResponse;
import ru.verkhoturkin.ecommerce.entity.Customer;
import ru.verkhoturkin.ecommerce.entity.Order;
import ru.verkhoturkin.ecommerce.entity.OrderItem;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private final CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();

        String trackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(trackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(order::add);

        order.setShippingAddress(purchase.getShippingAddress());
        order.setBillingAddress(purchase.getBillingAddress());

        Customer customer = purchase.getCustomer();
        order.setCustomer(customer);
        customer.add(order);

        customerRepository.save(customer);
        return new PurchaseResponse(trackingNumber);
    }

    private String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}

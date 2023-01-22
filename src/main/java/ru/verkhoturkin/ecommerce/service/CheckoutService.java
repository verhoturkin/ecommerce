package ru.verkhoturkin.ecommerce.service;

import ru.verkhoturkin.ecommerce.dto.Purchase;
import ru.verkhoturkin.ecommerce.dto.PurchaseResponse;

public interface CheckoutService {

    PurchaseResponse placeOrder(Purchase purchase);
}

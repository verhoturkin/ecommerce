package ru.verkhoturkin.ecommerce.controller;

import org.springframework.web.bind.annotation.*;
import ru.verkhoturkin.ecommerce.dto.Purchase;
import ru.verkhoturkin.ecommerce.dto.PurchaseResponse;
import ru.verkhoturkin.ecommerce.service.CheckoutService;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/purchase")
    public PurchaseResponse placeOrder(@RequestBody Purchase purchase) {
        return checkoutService.placeOrder(purchase);
    }
}

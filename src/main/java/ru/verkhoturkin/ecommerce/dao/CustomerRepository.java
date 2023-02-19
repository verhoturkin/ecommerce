package ru.verkhoturkin.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.verkhoturkin.ecommerce.entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}

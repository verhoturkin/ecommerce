package ru.verkhoturkin.ecommerce.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.verkhoturkin.ecommerce.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

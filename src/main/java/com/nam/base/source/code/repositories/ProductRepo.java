package com.nam.base.source.code.repositories;

import com.nam.base.source.code.entities.Product;
import com.nam.base.source.code.enums.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, String> {
    List<Product> findProductsByStatusIsNot(ProductStatus status);
}

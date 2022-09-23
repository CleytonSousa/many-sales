package br.com.manysales.productapi.repositories;

import br.com.manysales.productapi.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}

package br.com.manysales.productapi.repositories;

import br.com.manysales.productapi.entities.Category;
import br.com.manysales.productapi.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    List<Supplier> findByNameIgnoreCaseContaining(String name);
}

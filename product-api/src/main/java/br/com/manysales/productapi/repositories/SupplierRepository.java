package br.com.manysales.productapi.repositories;

import br.com.manysales.productapi.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
}

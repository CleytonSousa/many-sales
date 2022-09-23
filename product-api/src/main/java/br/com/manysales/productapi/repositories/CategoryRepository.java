package br.com.manysales.productapi.repositories;

import br.com.manysales.productapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}

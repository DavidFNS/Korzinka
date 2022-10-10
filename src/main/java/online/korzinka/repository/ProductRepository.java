package online.korzinka.repository;

import online.korzinka.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Stream<Product> findAllBy();
    Stream<Product> findAllByIdLessThan(Integer id);
}

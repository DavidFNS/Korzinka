package online.korzinka.repository;

import online.korzinka.entity.ProductTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTypesRepository extends JpaRepository<ProductTypes, Integer> {
    @Query("select pt from ProductTypes pt where pt.barcode = ?1 and pt.name = ?2")
    List<ProductTypes> findAllByBarcodeAndName(String barcodeeee, String nomi);
}
        
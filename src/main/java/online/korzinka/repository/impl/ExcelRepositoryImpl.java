package online.korzinka.repository.impl;

import lombok.RequiredArgsConstructor;
import online.korzinka.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ExcelRepositoryImpl {
    private final EntityManager entityManager;

    public Page<Product> getAllProductsByParams(Pageable pageable,
                                                MultiValueMap<String, String> map){
        StringBuilder builder = new StringBuilder();
        addToBuilder(builder, map);

        String stringselect = "select * from products where 1 = 1" + builder;
        String stringcount = "select count(1) from products where 1 = 1" + builder;

        Query querySelect = entityManager.createNativeQuery(stringselect, Product.class);
        Query queryCount = entityManager.createNativeQuery(stringcount, Integer.class);

        setParamater(querySelect, map);
        setParamater(queryCount, map);

        if (pageable != null){
            querySelect.setFirstResult(pageable.getPageSize()* pageable.getPageNumber());
            queryCount.setMaxResults(pageable.getPageNumber());
        }

        int count = queryCount.getFirstResult();
        List<Product> list = querySelect.getResultList();

        return new PageImpl(list, pageable, count);
    }

    public void addToBuilder(StringBuilder stringBuilder, MultiValueMap<String, String> map){
        if (map.containsKey("price")){
            stringBuilder.append(" and price = :cost");
        }
        if (map.containsKey("amount")){
            stringBuilder.append(" and amount = :amount");
        }
        if (map.containsKey("name")){
            stringBuilder.append(" and name = :name_product");
        }
    }

    public void setParamater(Query query, MultiValueMap<String, String> map){
        if (map.containsKey("price")){
            query.setParameter("cost", Double.parseDouble(map.getFirst("price")));
        }
        if (map.containsKey("name")){
            query.setParameter("name_product", map.getFirst("name"));
        }
        if (map.containsKey("amount")){
            query.setParameter("amount", Integer.parseInt(map.getFirst("amount")));
        }
    }
}
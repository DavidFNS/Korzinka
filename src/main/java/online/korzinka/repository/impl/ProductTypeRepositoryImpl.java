package online.korzinka.repository.impl;

import lombok.RequiredArgsConstructor;
import online.korzinka.entity.ProductTypes;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductTypeRepositoryImpl {

    private final EntityManager entityManager;
    private final SessionFactory sessionFactory;

    // Write query with EntityManager using JPA
    public List<ProductTypes> findAll(){
//        Query query = entityManager.createQuery("select pt from ProductTypes pt join fetch pt.products");

//        return query.getResultList();
        return null;
    }

    // Write query with SessionFactory using Hibernate
    //---------------------- 1 - HQL -----------------------//
    public List<ProductTypes> findByIdUsingHQL(Integer idi){
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select pt from ProductTypes pt where pt.id = :idi");

        return query.getResultList();
    }

    //---------------------- 2 - NativeQuery -----------------------//
    public List<ProductTypes> findAllTypesUsingNativeQuery(){
        Session session = sessionFactory.openSession();
        Query query = session.createNativeQuery("select id, name, unit_id, barcode from product_types pt", ProductTypes.class);

        return query.getResultList();
    }

    //---------------------- 3 - NamedQuery -----------------------//
    public List<ProductTypes> findAllTypesUsingNamedQuery(){
        Session session = sessionFactory.openSession();
        Query query = session.createNamedQuery("findAll", ProductTypes.class);

        return query.getResultList();
    }
}

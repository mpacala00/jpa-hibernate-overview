package com.github.mpacala00;

import com.github.mpacala00.entity.Product;
import com.github.mpacala00.persistence.PersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUnit;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        // when working whit any ORM you never directly work with database,
        //  you are working with the context
        // context - collection of instances of entities

        // obtaining EntityManagerFactory while using persistence.xml for config
        //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new PersistenceUnitInfo(), new HashMap<>()); // empty hashmap -> no parameters

        // through entityManager you operate on Hibernate context
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            //all changes to entities will be synchronized with database after getTransaction().commit()
            entityManager.getTransaction().begin();
            //put operations on entities here:
            Product product = new Product();
//            product.setId(1L);
//            product.setName("Some product");
            product.setId(2L);
            product.setName("Other product");

            // persist() -> add this to the context
            // NOT AN INSERT
            entityManager.persist(product);

            entityManager.getTransaction().commit();
        } finally {
            // clear persistence context; detach all entities from the context
            entityManager.clear();
        }


    }
}
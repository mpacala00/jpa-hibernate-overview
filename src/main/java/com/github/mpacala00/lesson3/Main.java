package com.github.mpacala00.lesson3;

import com.github.mpacala00.lesson2.entity.Employee;
import com.github.mpacala00.lesson2.persistence.PersistenceUnitInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Map of properties that will be passed to EntityManagerFactory
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.show_sql", "true");
        // Property for allowing hibernate to make changes to the structure of database
        //  "none"   - default value, as should be in production; use other values only for example projects!
        //  "create" - will allow for creation of tables from classes with @Entity annotation
        //  "update" - will create, but not drop tables, so it keeps the data
        properties.put("hibernate.hbm2ddl.auto", "create"); // none, create, update

        EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new PersistenceUnitInfo(), properties);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            // find() vs getReference()
            var e1 = new Employee();
            e1.setId(1);
            e1.setName("John");
            e1.setAddress("Address");

            // Adding e1 to the context (NOT AN INSERT!)
            entityManager.persist(e1);

            // find() first checks the context, if entity is found in the context, no select queries will be executed
            e1 = entityManager.find(Employee.class, 1);
            System.out.println(e1);


            // refresh()
            var e2 = new Employee();
            e2.setId(2);
            e2.setName("Adam");
            e2.setAddress("Address");
            entityManager.persist(e2);
            entityManager.getTransaction().commit();
            entityManager.detach(e2); // for demonstration purposes

            entityManager.getTransaction().begin();

            // You get the reference to the entity - no query is executed until e2 is mentioned in the code
            // Also exception will be thrown when not present in db, unlike find(), which would return null
            e2 = entityManager.getReference(Employee.class, 2);

            // When e2 is referenced in sout the query will be executed;
            //  allows for lazy data loading from database
            System.out.println("sout for e2: " + e2);

            // refresh the entity with data present in the database
            e1.setName("Daniel");
            entityManager.refresh(e1); // reverts "Daniel" to what's present in db, kinda like undo
            System.out.println("e1 after refresh: " + e1);

            entityManager.getTransaction().commit();
        } finally {
            entityManager.clear();
        }
    }
}
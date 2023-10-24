package com.github.mpacala00;

import com.github.mpacala00.entity.Employee;
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
        //  it represents the context
        // usually we use 1 entityManagerFactory, but we can create multiple
        //  entity managers
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

        getEntityByPrimaryKey(entityManagerFactory);
    }

    public static void getEntityByPrimaryKey(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();

        // remember to include entity classes in PersistenceUnitInfo: Employee
        try {
            em.getTransaction().begin();

            // to find something in the database use find()
            // instance of employee will be automatically part of the context after it is fetched from database
            //  which means when we try to find the same entity, no query will be executed
            //  but taken from the context
            Employee e1 = em.find(Employee.class, 1);
            System.out.println(e1);

            // in this example we set a new value for employee
            //  and in will in fact will be updated in the database
            //  despite no save / insert / persist operation being done
            // in any JPA implementation there is no such thing as update
            // at the end of the transaction the context is mirrored to database
            Employee e2 = em.find(Employee.class, 1);
            e2.setName("mpacala");
            System.out.println(e1);

            // note: query is only made when there is a change at the end of transaction
            // if the name in db was mpacala, no query will be executed
            e2.setName("new name");
            e2.setName("mpacala");

            // another example:
            // nothing will be removed from the database
            // no data have changed
            em.remove(e1);
            Employee e3 = new Employee();
            e3.setId(1);
            e3.setName("mpacala");
            e3.setAddress("Some street 2");
            em.persist(e3);

            // overview of all EM operations:
            /*
            em.persist(); -> Adding an entity in the context (representing a new record)
            em.find();    -> Finds by PK. Get from DB and add it to the context if it doesn't already exist
            em.remove();  -> Marking an entity for removal
            em.merge();   -> Merges an entity from outside the context to the context (entity should exist in db!)
            em.refresh(); -> Mirror the context from the database
            em.detach();  -> Taking the entity out of the context
            em.getReference()
             */

            // Example of em.merge():
            Employee e4 = new Employee();
            e4.setId(4);
            e4.setName("Mary");
            e4.setAddress("Mary street");
            // normally, nothing would happen to this point, as calling a constructor of an entity
            //  does not make it part of the context
            // if something is not in the context, the Hibernate does not see it

            em.merge(e4);
            // but with merge operation, it will replace existing employee of id=1 in the database
            //  (if the transaction would end right after and nothing more was changed)

            em.getTransaction().commit(); // end of transaction
        } finally {
            em.close();
        }
    }
}
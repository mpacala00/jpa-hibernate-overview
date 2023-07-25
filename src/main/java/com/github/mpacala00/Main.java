package com.github.mpacala00;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {

        // obtaining EntityManagerFactory while using persistence.xml for config
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");

        // through entityManager you operate on Hibernate context
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            //all changes to entities will be synchronized with database after getTransaction().commit()
            entityManager.getTransaction().begin();
            //put operations on entities here:

            entityManager.getTransaction().commit();
        } finally {
            // clear persistence context; detach all entities from the context
            entityManager.clear();
        }


    }
}
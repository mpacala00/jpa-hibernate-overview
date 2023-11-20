package com.github.mpacala00.lesson3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class EntityIdGeneratedValue {

    @Id
    // GenerationTypes:
    // AUTO     - default specified by JPA implementation
    // IDENTITY - dbms will generate ids incrementally (will not work with some dbs), e.g. auto increment
    // TABLE    - creates table "hibernate_sequences" to generate ids, not recommended, but highly compatible
    // SEQUENCE - creates a sequence in db for id generation, falls back to TABLE if dbms doesn't support sequences
    // UUID     - generates a UUID to be used as id, typically requires String field type
    @GeneratedValue(strategy = GenerationType.AUTO) // AUTO, IDENTITY, SEQUENCE, TABLE, UUID
    Integer id;
}

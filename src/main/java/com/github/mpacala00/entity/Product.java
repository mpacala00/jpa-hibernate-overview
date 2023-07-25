package com.github.mpacala00.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity // mark a class as JPA entity
public class Product {

    @Id // It is mandatory to mark primary key with @Id annotation
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

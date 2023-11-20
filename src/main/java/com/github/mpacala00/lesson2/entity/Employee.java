package com.github.mpacala00.lesson2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/*
 * difference between
 * @Entity(name="") and
 * @Table(name="")
 *
 * entity name: name of certain entity in the hibernate context
 * table name: name of the table in database
 */
@Entity
// name not mandatory in this case, as class name is the same as table
// this name attribute will be used in the queries
// other cool @Table params: schema, uniqueConstraints, indexes
@Table(name = "employee")
public class Employee {

    // @Id is mandatory for any Entity
    //  even if you have a table without simple PK
    //  then you have to create a composed PK
    @Id
    // @Column - specified details about column
    //  like field's name is different from what's in the db
    //  not necessary if the same as in the table
    @Column(name = "id")
    private Integer id;
    private String name;
    private String address;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

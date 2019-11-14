package com.example.core;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity(name="BOOK") //By default, Hibernate generates the table names in lower case, by standard, DB tables should be uppercase
public class Book {
    @Id
    @GeneratedValue
    // The above line is as good as declaring @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String author;
}

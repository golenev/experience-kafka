package com.experience_kafka.model;

import jakarta.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String sex;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}

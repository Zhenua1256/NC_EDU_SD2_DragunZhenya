package com.netcracker.dragun.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Long price;
    @Column(name = "section")
    private String section;
    @Column(name = "description")
    private String description;
    @Column(name = "company_id")
    private Long companyId;
}

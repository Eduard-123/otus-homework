package ru.otus.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "addresses")
public class AddressDataSet {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;
}
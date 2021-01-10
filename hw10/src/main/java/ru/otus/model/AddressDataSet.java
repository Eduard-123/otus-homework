package ru.otus.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(exclude = "id")
@Table(name = "addresses")
public class AddressDataSet {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;
}
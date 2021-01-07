package ru.otus.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PhoneDataSet {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name = "number")
    private String number;
}
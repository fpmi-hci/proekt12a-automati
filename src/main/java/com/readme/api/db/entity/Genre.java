package com.readme.api.db.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "genre")
@Data
public class Genre {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "name", unique = true)
    @NotBlank(message = "Genre name should be not empty")
    private String name;
}

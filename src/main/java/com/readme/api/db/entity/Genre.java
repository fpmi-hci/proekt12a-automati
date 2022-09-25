package com.readme.api.db.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "genre")
@Data
public class Genre {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;
}

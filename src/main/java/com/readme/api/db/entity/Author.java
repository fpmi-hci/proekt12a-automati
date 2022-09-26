package com.readme.api.db.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "author")
@Data
public class Author {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "full_name")
    private String fullName;
}

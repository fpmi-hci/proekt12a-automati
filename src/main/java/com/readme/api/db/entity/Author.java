package com.readme.api.db.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

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

    @JsonIgnore
    @ManyToMany(mappedBy = "authors")
    private List<Book> books;

}

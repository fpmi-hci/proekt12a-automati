package com.readme.api.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "book")
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", length = 1024)
    private String description;

    @Column(name = "cost")
    private BigDecimal cost;

    @Column(name = "book_file")
    private String bookFile;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany(mappedBy = "books")
    private List<Author> authors;


    @ManyToMany
    @JoinTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<Genre> genres;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Comment> comments;
}

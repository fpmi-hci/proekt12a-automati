package com.readme.api.rest.dto;

import com.readme.api.db.entity.Book;
import com.readme.api.db.entity.User;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.List;

@Data
public class CartRequestDto {

    private long id;

    private List<Long> books;
}

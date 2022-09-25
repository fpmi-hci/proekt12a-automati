package com.readme.api.rest.dto;

import com.readme.api.db.entity.Book;
import com.readme.api.db.entity.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
public class CommentRequestDto {

    private long id;

    private long bookId;

    private String description;

    private int rate;
}

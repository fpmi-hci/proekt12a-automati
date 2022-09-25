package com.readme.api.rest.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserOrderRequestDto {

    private long id;

    private long userId;

    private List<Long> books;
}

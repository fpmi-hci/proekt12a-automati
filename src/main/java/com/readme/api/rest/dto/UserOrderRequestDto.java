package com.readme.api.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class UserOrderRequestDto {

    private long id;

    @NotNull(message = "List of books in the order must be not null")
    private List<Long> books;
}

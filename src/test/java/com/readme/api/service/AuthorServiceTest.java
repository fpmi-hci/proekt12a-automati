package com.readme.api.service;

import com.readme.api.db.entity.Author;
import com.readme.api.db.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @InjectMocks
    private AuthorService authorService;

    @Mock
    private AuthorRepository authorRepository;

    @Test
    public void testFindByIdsListShouldReturnEmptyIfIdsIsNull(){
        List<Author> result = authorService.findByIdList(null);

        assertEquals(result, Collections.emptyList());
    }

}
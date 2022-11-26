package com.readme.api.rest;

import com.readme.api.db.entity.Comment;
import com.readme.api.rest.dto.CommentRequestDto;
import com.readme.api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody CommentRequestDto comment,
                                              @RequestHeader("Authorization") String currentUserToken) {
        Comment addedComment = commentService.addComment(comment, currentUserToken);
        return new ResponseEntity<>(addedComment, HttpStatus.ACCEPTED);
    }
}

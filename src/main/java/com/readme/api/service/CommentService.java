package com.readme.api.service;

import com.readme.api.db.entity.Comment;
import com.readme.api.db.entity.User;
import com.readme.api.db.repository.CommentRepository;
import com.readme.api.mapper.CommentMapper;
import com.readme.api.rest.dto.CommentRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@AllArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;
    private UserService userService;
    private CommentMapper commentMapper;

    @Transactional
    public Comment addComment(CommentRequestDto request, String currentUserToken) {
        User user = userService.findUserByToken(currentUserToken);
        Comment comment = commentMapper.requestToEntity(request);
        comment.setUser(user);
        return commentRepository.save(comment);
    }
}

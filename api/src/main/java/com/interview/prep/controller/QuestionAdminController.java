package com.interview.prep.controller;

import com.interview.prep.model.Question;
import com.interview.prep.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin/questions")
@CrossOrigin(origins = "*") // Adjust for security in production!
public class QuestionAdminController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("/add")
    public ResponseEntity<?> addQuestion(@RequestBody Question question) {
        Optional<Question> existing = questionService.findByTopicAndTextAndAnswer(
                question.getTopic(),
                question.getQuestionText(),
                question.getCorrectAnswer()
        );

        if (existing.isPresent()) {
            // Return full question object with 409 Conflict status
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(existing.get());
        }

        Question saved = questionService.addQuestion(question);
        return ResponseEntity.ok(saved);
    }


}

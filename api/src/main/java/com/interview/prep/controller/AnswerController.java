package com.interview.prep.controller;

import com.interview.prep.dto.AnswerRequest;
import com.interview.prep.dto.AnswerResponse;
import com.interview.prep.model.Question;
import com.interview.prep.service.QuestionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
@CrossOrigin("*")
public class AnswerController {

    private final QuestionService questionService;

    public AnswerController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping("/check")
    public AnswerResponse checkAnswer(@RequestBody AnswerRequest request) {
        // ✅ Use String as ID for MongoDB
        Question question = questionService.getQuestionById(String.valueOf(request.getQuestionId()));

        if (question == null) {
            return new AnswerResponse(0, "N/A", "Question not found.");
        }

        // ✅ Basic mock scoring
        int score = request.getUserAnswer().trim().equalsIgnoreCase(question.getCorrectAnswer().trim()) ? 100 : 50;

        return new AnswerResponse(
                score,
                question.getCorrectAnswer(),
                "Review: " + question.getTopic() + " fundamentals."
        );
    }
}

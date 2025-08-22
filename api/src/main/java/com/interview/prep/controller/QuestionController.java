// Add these methods to your existing QuestionController or create a new controller

package com.interview.prep.controller;

import com.interview.prep.model.Question;
import com.interview.prep.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "*")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{topic}")
    public ResponseEntity<List<Question>> getQuestionsByTopic(@PathVariable String topic) {
        List<Question> questions = questionService.getQuestionsByTopic(topic);
        return ResponseEntity.ok(questions);
    }

    // New endpoint to get questions statistics
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getQuestionsStats() {
        List<Question> allQuestions = questionService.getAllQuestions();

        Map<String, Long> topicCounts = allQuestions.stream()
                .collect(Collectors.groupingBy(Question::getTopic, Collectors.counting()));

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalQuestions", allQuestions.size());
        stats.put("totalTopics", topicCounts.size());
        stats.put("topicBreakdown", topicCounts);

        return ResponseEntity.ok(stats);
    }

    // New endpoint to get all available topics
    @GetMapping("/topics")
    public ResponseEntity<List<String>> getTopics() {
        List<Question> allQuestions = questionService.getAllQuestions();

        List<String> topics = allQuestions.stream()
                .map(Question::getTopic)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        return ResponseEntity.ok(topics);
    }

    // New endpoint to get questions by multiple topics (for skills-based queries)
    @PostMapping("/by-skills")
    public ResponseEntity<List<Question>> getQuestionsBySkills(@RequestBody List<String> skills) {
        List<Question> questions = new ArrayList<>();

        for (String skill : skills) {
            List<Question> skillQuestions = questionService.getQuestionsByTopic(skill.toUpperCase());
            questions.addAll(skillQuestions);
        }

        // Remove duplicates based on question ID
        List<Question> uniqueQuestions = questions.stream()
                .collect(Collectors.toMap(
                        Question::getId,
                        question -> question,
                        (existing, replacement) -> existing))
                .values()
                .stream()
                .collect(Collectors.toList());

        return ResponseEntity.ok(uniqueQuestions);
    }

    // New endpoint to get random questions
    @GetMapping("/random")
    public ResponseEntity<List<Question>> getRandomQuestions(
            @RequestParam(defaultValue = "10") int count,
            @RequestParam(required = false) String topic) {

        List<Question> questions;
        if (topic != null && !topic.trim().isEmpty()) {
            questions = questionService.getQuestionsByTopic(topic);
        } else {
            questions = questionService.getAllQuestions();
        }

        Collections.shuffle(questions);
        List<Question> randomQuestions = questions.stream()
                .limit(count)
                .collect(Collectors.toList());

        return ResponseEntity.ok(randomQuestions);
    }
}

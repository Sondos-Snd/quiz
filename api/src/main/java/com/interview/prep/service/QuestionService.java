// service/QuestionService.java
package com.interview.prep.service;

import com.interview.prep.model.Question;
import com.interview.prep.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getQuestionsByTopic(String topic) {
        return questionRepository.findByTopic(topic);
    }

    public Question getQuestionById(String id) {
        Optional<Question> optionalQuestion = questionRepository.findById(id);
        return optionalQuestion.orElse(null);
    }
    public Optional<Question> findByTopicAndTextAndAnswer(String topic, String text, String answer) {
        return questionRepository.findByTopicAndQuestionTextAndCorrectAnswer(topic, text, answer);
    }

    public Question addQuestion(Question question) {
        Optional<Question> existingQuestion = questionRepository
                .findByTopicAndQuestionTextAndCorrectAnswer(question.getTopic(),  question.getQuestionText(),question.getCorrectAnswer());

        if (existingQuestion.isPresent()) {
            return existingQuestion.get(); // أو: throw new IllegalStateException("Question already exists");
        }

        return questionRepository.save(question);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();

    }

}

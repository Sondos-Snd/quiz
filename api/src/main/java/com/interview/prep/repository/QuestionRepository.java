// repository/QuestionRepository.java
package com.interview.prep.repository;

import com.interview.prep.model.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByTopic(String topic);

    Optional<Question> findByTopicAndQuestionTextAndCorrectAnswer(String topic, String questionText, String correctAnswer);
}

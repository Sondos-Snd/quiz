package com.interview.prep.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor  // Required by Spring Data MongoDB
@Document(collection = "quizcol")  // Your collection name
public class Question {
    @Id
    private String id;  // MongoDB uses String ObjectId by default
    private String topic;
    private String questionText;
    private String correctAnswer;
}

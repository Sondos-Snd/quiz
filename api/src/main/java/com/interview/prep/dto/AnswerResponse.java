// dto/AnswerResponse.java
package com.interview.prep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AnswerResponse {
    private int score;
    private String recommendedAnswer;
    private String futureSkills;
}

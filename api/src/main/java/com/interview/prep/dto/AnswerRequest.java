// dto/AnswerRequest.java
package com.interview.prep.dto;

import lombok.Data;

@Data
public class AnswerRequest {
    private String questionId;
    private String answer;
}

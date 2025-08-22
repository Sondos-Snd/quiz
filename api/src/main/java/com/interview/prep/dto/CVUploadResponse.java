package com.interview.prep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CVUploadResponse {
    private String message;
    private int generatedQuestions;
    private List<String> extractedSkills;
}
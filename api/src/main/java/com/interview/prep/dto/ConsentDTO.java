// src/main/java/com/interview/prep/web/dto/ConsentDTO.java
package com.interview.prep.dto;
import java.time.OffsetDateTime;
import java.util.Map;
public record ConsentDTO(Long id, String policyVersion, OffsetDateTime givenAt, Map<String,Object> scope) {}

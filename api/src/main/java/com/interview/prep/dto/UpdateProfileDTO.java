// src/main/java/com/interview/prep/web/dto/UpdateProfileDTO.java
package com.interview.prep.dto;
import java.util.List;
import java.util.Map;

public record UpdateProfileDTO(
        String phone,
        String locale,
        List<ConsentUpsert> consents // full replace (id optional)
){
    public record ConsentUpsert(Long id, String policyVersion, Map<String,Object> scope){}
}

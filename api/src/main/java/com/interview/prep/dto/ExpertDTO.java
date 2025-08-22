//package com.interview.prep.dto;
//
//import com.interview.prep.domain.Enums.ExpertStatus;
//import com.interview.prep.domain.Enums.Profession;
//import jakarta.validation.constraints.*;
//import lombok.*;
//import java.math.BigDecimal;
//
//@Data @NoArgsConstructor @AllArgsConstructor @Builder
//public class ExpertDTO {
//    private Long id;
//
//    @NotNull
//    private Long userId;
//
//    @NotNull
//    private Profession profession;
//
//    private String regNumber;
//    private String barOrdre;
//
//    // JSONB blobs (array/object). Keep as Object for flexibility.
//    private Object specialties;
//    private Object languages;
//
//    private String bio;
//    private BigDecimal rateHourly;
//    private Object locations;
//
//    @NotNull
//    private ExpertStatus status;
//}

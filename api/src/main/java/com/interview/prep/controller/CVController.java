package com.interview.prep.controller;

import com.interview.prep.service.CVService;
import com.interview.prep.dto.CVUploadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/api/cv")
@CrossOrigin(origins = "*") // Adjust for security in production!
public class CVController {

    @Autowired
    private CVService cvService;

    @PostMapping("/import")
    public ResponseEntity<?> importCV(@RequestParam("cv") MultipartFile file) {
        System.out.println("hit");
        try {
            // Validate file
            if (file.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new CVUploadResponse("No file provided", 0, null));
            }

            // Validate file size (5MB limit)
            if (file.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity
                        .status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .body(new CVUploadResponse("File size exceeds 5MB limit", 0, null));
            }

            // Validate file type
            String contentType = file.getContentType();
            if (!isValidFileType(contentType)) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(new CVUploadResponse("Invalid file type. Only PDF, DOC, DOCX, TXT, CSV, and Excel files are allowed", 0, null));
            }

            // Special case: structured CSV import
            if ("text/csv".equals(contentType)) {
                try (InputStream inputStream = file.getInputStream()) {
                    int imported = cvService.importStructuredQuestionsFromCSV(inputStream);
                    return ResponseEntity.ok(new CVUploadResponse("CSV import successful", imported, null));
                }
            }

            // Default: extract skills and generate questions
            CVUploadResponse response = cvService.processCVAndGenerateQuestions(file);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CVUploadResponse("Error processing CV: " + e.getMessage(), 0, null));
        }
    }

    private boolean isValidFileType(String contentType) {
        return contentType != null && (
                contentType.equals("application/pdf") ||
                        contentType.equals("application/msword") ||
                        contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") ||
                        contentType.equals("text/plain") ||
                        contentType.equals("application/vnd.ms-excel") || // .xls
                        contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") || // .xlsx
                        contentType.equals("text/csv") // .csv
        );
    }
}

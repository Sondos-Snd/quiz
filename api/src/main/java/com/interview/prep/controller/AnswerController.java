package com.interview.prep.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answers")
@CrossOrigin(origins = "*") // Allow CORS if needed
public class AnswerController {

  /**
   * GET endpoint for checking answers via query parameters
   * URL: /api/answers/check?question=1&answer=Paris
   */
  @GetMapping("/check")
  public ResponseEntity<String> checkAnswerGet(
    @RequestParam(required = false) String question,
    @RequestParam(required = false) String answer) {

    if (question == null || answer == null) {
      return ResponseEntity.badRequest()
        .body("Usage: /api/answers/check?question=1&answer=yourAnswer");
    }

    // Your answer checking logic here
    boolean isCorrect = checkAnswer(question, answer);

    return ResponseEntity.ok(
      String.format("Question: %s, Answer: %s, Correct: %s",
        question, answer, isCorrect)
    );
  }

  /**
   * POST endpoint for checking answers via JSON body
   * Body: {"questionId": "1", "answer": "Paris"}
   */
  @PostMapping("/check")
  public ResponseEntity<AnswerCheckResponse> checkAnswerPost(
    @RequestBody(required = false) AnswerCheckRequest request) {

    // Check if request body is null
    if (request == null) {
      return ResponseEntity.badRequest()
        .body(new AnswerCheckResponse("Request body is required", false, "error"));
    }

    // Check if required fields are null or empty
    if (request.getQuestionId() == null || request.getQuestionId().trim().isEmpty() ||
      request.getAnswer() == null || request.getAnswer().trim().isEmpty()) {
      return ResponseEntity.badRequest()
        .body(new AnswerCheckResponse("Both questionId and answer are required", false, "error"));
    }

    try {
      // Your answer checking logic here
      boolean isCorrect = checkAnswer(request.getQuestionId(), request.getAnswer());

      return ResponseEntity.ok(
        new AnswerCheckResponse("Answer checked successfully", isCorrect, "success")
      );
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new AnswerCheckResponse("Error processing request: " + e.getMessage(), false, "error"));
    }
  }

  /**
   * GET endpoint to list available endpoints
   */
  @GetMapping("/")
  public ResponseEntity<String> getEndpoints() {
    return ResponseEntity.ok(
      "Quiz Answers API v1.0\n\n" +
        "Available endpoints:\n" +
        "GET  /api/answers/ - This help page\n" +
        "GET  /api/answers/info - API information\n" +
        "GET  /api/answers/check?question=<id>&answer=<answer> - Check answer via URL params\n" +
        "POST /api/answers/check - Check answer via JSON body\n\n" +
        "POST JSON format: {\"questionId\": \"1\", \"answer\": \"your_answer\"}"
    );
  }

  /**
   * GET endpoint for API info
   */
  @GetMapping("/info")
  public ResponseEntity<String> getInfo() {
    return ResponseEntity.ok("Quiz Answers API v1.0 - Ready to check your answers!");
  }

  /**
   * Test endpoint to verify POST is working
   */
  @PostMapping("/test")
  public ResponseEntity<String> testPost(@RequestBody(required = false) String body) {
    if (body == null) {
      return ResponseEntity.ok("POST endpoint is working! Send JSON body to test further.");
    }
    return ResponseEntity.ok("POST received: " + body);
  }

  /**
   * Your actual answer checking logic
   */
  private boolean checkAnswer(String questionId, String answer) {
    // TODO: Implement your actual answer checking logic here
    // This could involve database lookup, comparing with correct answers, etc.

    // For now, return a simple mock result based on some basic logic
    if (answer == null || answer.trim().isEmpty()) {
      return false;
    }

    // Mock logic: question 1 answer is "Paris", question 2 answer is "London"
    switch (questionId) {
      case "1":
        return "Paris".equalsIgnoreCase(answer.trim());
      case "2":
        return "London".equalsIgnoreCase(answer.trim());
      default:
        return answer.trim().length() > 2; // Any answer with more than 2 characters is "correct"
    }
  }

  /**
   * Request DTO for POST requests
   */
  public static class AnswerCheckRequest {
    private String questionId;
    private String answer;

    // Constructors
    public AnswerCheckRequest() {}

    public AnswerCheckRequest(String questionId, String answer) {
      this.questionId = questionId;
      this.answer = answer;
    }

    // Getters and Setters
    public String getQuestionId() { return questionId; }
    public void setQuestionId(String questionId) { this.questionId = questionId; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }

    @Override
    public String toString() {
      return "AnswerCheckRequest{" +
        "questionId='" + questionId + '\'' +
        ", answer='" + answer + '\'' +
        '}';
    }
  }

  /**
   * Response DTO for structured responses
   */
  public static class AnswerCheckResponse {
    private String message;
    private boolean correct;
    private String timestamp;
    private String status;

    // Constructors
    public AnswerCheckResponse() {
      this.timestamp = java.time.Instant.now().toString();
    }

    public AnswerCheckResponse(String message, boolean correct, String status) {
      this.message = message;
      this.correct = correct;
      this.status = status;
      this.timestamp = java.time.Instant.now().toString();
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public boolean isCorrect() { return correct; }
    public void setCorrect(boolean correct) { this.correct = correct; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
      return "AnswerCheckResponse{" +
        "message='" + message + '\'' +
        ", correct=" + correct +
        ", timestamp='" + timestamp + '\'' +
        ", status='" + status + '\'' +
        '}';
    }
  }
}

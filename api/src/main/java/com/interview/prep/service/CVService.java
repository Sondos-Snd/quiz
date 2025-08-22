package com.interview.prep.service;

import com.interview.prep.dto.CVUploadResponse;
import com.interview.prep.model.Question;
import com.interview.prep.repository.QuestionRepository;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CVService {

    @Autowired
    private QuestionRepository questionRepository;

    // Predefined skill patterns and related questions
    private static final Map<String, List<String>> SKILL_QUESTIONS = new HashMap<>();

    static {
        SKILL_QUESTIONS.put("java", Arrays.asList(
                "What are the main principles of Object-Oriented Programming in Java?",
                "Explain the difference between ArrayList and LinkedList in Java.",
                "What is the difference between == and equals() method in Java?"
        ));

        SKILL_QUESTIONS.put("python", Arrays.asList(
                "What are Python decorators and how do you use them?",
                "Explain the difference between list and tuple in Python.",
                "What is the Global Interpreter Lock (GIL) in Python?"
        ));

        SKILL_QUESTIONS.put("javascript", Arrays.asList(
                "What is the difference between var, let, and const in JavaScript?",
                "Explain closures in JavaScript with an example.",
                "What is the event loop in JavaScript?"
        ));

        SKILL_QUESTIONS.put("react", Arrays.asList(
                "What are React hooks and why were they introduced?",
                "Explain the difference between state and props in React.",
                "What is the virtual DOM in React?"
        ));

        SKILL_QUESTIONS.put("angular", Arrays.asList(
                "What is dependency injection in Angular?",
                "Explain the difference between Component and Directive in Angular.",
                "What are Angular services and how do you create them?"
        ));

        SKILL_QUESTIONS.put("spring", Arrays.asList(
                "What is Inversion of Control (IoC) in Spring Framework?",
                "Explain the difference between @Component, @Service, and @Repository annotations.",
                "What is Spring Boot and how does it differ from Spring Framework?"
        ));

        SKILL_QUESTIONS.put("sql", Arrays.asList(
                "What is the difference between INNER JOIN and LEFT JOIN?",
                "What are database indexes and how do they improve performance?",
                "Explain ACID properties in database transactions."
        ));

        SKILL_QUESTIONS.put("mongodb", Arrays.asList(
                "What are the advantages of MongoDB over relational databases?",
                "Explain the concept of sharding in MongoDB.",
                "What is the difference between embedded and referenced documents in MongoDB?"
        ));

        SKILL_QUESTIONS.put("docker", Arrays.asList(
                "What is the difference between Docker image and Docker container?",
                "Explain the purpose of Dockerfile and its common commands.",
                "What are Docker volumes and why are they important?"
        ));

        SKILL_QUESTIONS.put("kubernetes", Arrays.asList(
                "What is the difference between Deployment and StatefulSet in Kubernetes?",
                "Explain the concept of Pods in Kubernetes.",
                "What are Kubernetes Services and their types?"
        ));

        SKILL_QUESTIONS.put("aws", Arrays.asList(
                "What is the difference between EC2 and Lambda in AWS?",
                "Explain S3 storage classes and their use cases.",
                "What is Auto Scaling in AWS and how does it work?"
        ));

        SKILL_QUESTIONS.put("microservices", Arrays.asList(
                "What are the advantages and challenges of microservices architecture?",
                "How do you handle data consistency in microservices?",
                "What are the common patterns for microservices communication?"
        ));
    }

    public CVUploadResponse processCVAndGenerateQuestions(MultipartFile file) throws IOException {
        // Extract text from CV
        String cvText = extractTextFromFile(file);

        // Extract skills from CV text
        List<String> extractedSkills = extractSkills(cvText);

        // Generate questions based on extracted skills
        int generatedQuestions = generateQuestionsFromSkills(extractedSkills);

        String message = String.format(
                "CV processed successfully! Generated %d questions based on %d skills found: %s",
                generatedQuestions,
                extractedSkills.size(),
                String.join(", ", extractedSkills)
        );

        return new CVUploadResponse(message, generatedQuestions, extractedSkills);
    }

    private String extractTextFromFile(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        InputStream inputStream = file.getInputStream();

        try {
            switch (contentType) {
                case "application/pdf":
                    return extractTextFromPDF(inputStream);
                case "application/msword":
                    return extractTextFromDOC(inputStream);
                case "application/vnd.openxmlformats-officedocument.wordprocessingml.document":
                    return extractTextFromDOCX(inputStream);
                case "text/plain":
                    return extractTextFromTXT(inputStream);
                case "application/vnd.ms-excel":
                    return extractTextFromXLS(inputStream);
                case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
                    return extractTextFromXLSX(inputStream);
                case "text/csv":
                    return extractTextFromCSV(inputStream);
                default:
                    throw new IllegalArgumentException("Unsupported file type: " + contentType);
            }
        } finally {
            inputStream.close();
        }
    }
    private String extractTextFromXLS(InputStream inputStream) throws IOException {
        StringBuilder text = new StringBuilder();
        try (org.apache.poi.hssf.usermodel.HSSFWorkbook workbook = new org.apache.poi.hssf.usermodel.HSSFWorkbook(inputStream)) {
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                var sheet = workbook.getSheetAt(i);
                for (var row : sheet) {
                    for (var cell : row) {
                        text.append(cell.toString()).append(" ");
                    }
                    text.append("\n");
                }
            }
        }
        return text.toString();
    }

    private String extractTextFromXLSX(InputStream inputStream) throws IOException {
        StringBuilder text = new StringBuilder();
        try (org.apache.poi.xssf.usermodel.XSSFWorkbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook(inputStream)) {
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                var sheet = workbook.getSheetAt(i);
                for (var row : sheet) {
                    for (var cell : row) {
                        text.append(cell.toString()).append(" ");
                    }
                    text.append("\n");
                }
            }
        }
        return text.toString();
    }

    private String extractTextFromCSV(InputStream inputStream) throws IOException {
        StringBuilder text = new StringBuilder();
        try (var reader = new java.io.InputStreamReader(inputStream);
             var csvReader = new com.opencsv.CSVReader(reader)) {
            String[] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                text.append(String.join(" ", nextLine)).append("\n");
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
        return text.toString();
    }

    public int importStructuredQuestionsFromCSV(InputStream inputStream) throws IOException {
        int importedCount = 0;
        try (var reader = new InputStreamReader(inputStream);
             var csvReader = new com.opencsv.CSVReaderBuilder(reader).withSkipLines(1).build()) { // Skip header
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                if (line.length >= 3) {
                    Question q = new Question();
                    q.setTopic(line[0].trim());
                    q.setQuestionText(line[1].trim());
                    q.setCorrectAnswer(line[2].trim());
                    questionRepository.save(q);
                    importedCount++;
                }
            }
        } catch (CsvValidationException e) {
            throw new RuntimeException("Failed to parse CSV", e);
        }
        return importedCount;
    }



    private String extractTextFromPDF(InputStream inputStream) throws IOException {
        try (PDDocument document = PDDocument.load(inputStream)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private String extractTextFromDOC(InputStream inputStream) throws IOException {
        try (HWPFDocument document = new HWPFDocument(inputStream);
             WordExtractor extractor = new WordExtractor(document)) {
            return extractor.getText();
        }
    }

    private String extractTextFromDOCX(InputStream inputStream) throws IOException {
        try (XWPFDocument document = new XWPFDocument(inputStream);
             XWPFWordExtractor extractor = new XWPFWordExtractor(document)) {
            return extractor.getText();
        }
    }

    private String extractTextFromTXT(InputStream inputStream) throws IOException {
        Scanner scanner = new Scanner(inputStream);
        StringBuilder text = new StringBuilder();
        while (scanner.hasNextLine()) {
            text.append(scanner.nextLine()).append("\n");
        }
        scanner.close();
        return text.toString();
    }

    private List<String> extractSkills(String cvText) {
        List<String> extractedSkills = new ArrayList<>();
        String lowerCaseText = cvText.toLowerCase();

        // Check for each skill in the predefined list
        for (String skill : SKILL_QUESTIONS.keySet()) {
            if (containsSkill(lowerCaseText, skill)) {
                extractedSkills.add(skill);
            }
        }

        // Additional skill patterns that might not be in our predefined list
        String[] additionalSkills = {
                "html", "css", "node.js", "express", "vue.js", "git", "jenkins",
                "maven", "gradle", "junit", "mockito", "redis", "elasticsearch",
                "rabbitmq", "kafka", "microservices", "rest api", "graphql",
                "oauth", "jwt", "ci/cd", "devops", "agile", "scrum"
        };

        for (String skill : additionalSkills) {
            if (containsSkill(lowerCaseText, skill) && !extractedSkills.contains(skill)) {
                extractedSkills.add(skill);
            }
        }

        return extractedSkills;
    }

    private boolean containsSkill(String text, String skill) {
        // Create a pattern that matches the skill as a whole word
        String pattern = "\\b" + Pattern.quote(skill.toLowerCase()) + "\\b";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        return m.find();
    }

    private int generateQuestionsFromSkills(List<String> skills) {
        int generatedCount = 0;

        for (String skill : skills) {
            List<String> questions = SKILL_QUESTIONS.get(skill.toLowerCase());
            if (questions != null) {
                for (String questionText : questions) {
                    // Check if question already exists
                    Optional<Question> existing = questionRepository.findByTopicAndQuestionTextAndCorrectAnswer(
                            skill.toUpperCase(),
                            questionText,
                            "Sample answer for " + questionText // You can improve this
                    );

                    if (!existing.isPresent()) {
                        Question question = new Question();
                        question.setTopic(skill.toUpperCase());
                        question.setQuestionText(questionText);
                        question.setCorrectAnswer("Sample answer for " + questionText); // You should provide better answers

                        questionRepository.save(question);
                        generatedCount++;
                    }
                }
            }
        }

        return generatedCount;
    }
}
import { HttpClient } from '@angular/common/http';
import { Component, signal, ViewChild } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AngularSvgIconModule } from 'angular-svg-icon';
import { QuestionPayload, QuestionService } from './services/table-filter.service';

@Component({
  selector: 'app-table',
  standalone: true,
  imports: [
    AngularSvgIconModule,
    FormsModule,
    CommonModule,
  ],
  templateUrl: './table.component.html',
  styleUrl: './table.component.css',
})
export class TableComponent {
  question = signal<QuestionPayload>({ topic: '', questionText: '', correctAnswer: '' });
  resultMessage = signal('');

  @ViewChild('questionForm') questionForm!: NgForm;

  constructor(private http: HttpClient, private questionService: QuestionService) {}

  async onSubmit(): Promise<void> {
    const { topic, questionText, correctAnswer } = this.question();

    if (!topic.trim() || !questionText.trim() || !correctAnswer.trim()) {
      this.resultMessage.set('❌ Please fill in all fields.');
      return;
    }

    try {
      const response = await this.questionService.addQuestion({
        topic,
        questionText,
        correctAnswer,
      });

      this.resultMessage.set(`✅ Question added with ID: ${response.id}`);
      this.question.set({ topic: '', questionText: '', correctAnswer: '' });
      this.questionForm.resetForm();
    } catch (error: any) {
      if (error.status === 409 && error.error?.correctAnswer) {
        this.resultMessage.set(`❌ Question already exists. Existing correct answer: "${error.error.correctAnswer}"`);
      } else {
        this.resultMessage.set(`❌ Network error: ${error.message ?? 'Unknown error'}`);
      }
    }
  }

  async onFileSelected(event: Event): Promise<void> {
    const input = event.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) {
      this.resultMessage.set('❌ No file selected.');
      return;
    }

    const file = input.files[0];

try {
  const response = await this.questionService.importCV(file).toPromise();
  if (response && response.generatedQuestions != null) {
    this.resultMessage.set(`✅ ${response.generatedQuestions} questions generated from CV.`);
  } else {
    this.resultMessage.set(`⚠️ CV import succeeded, but no questions were generated.`);
  }
} catch (error: any) {
  this.resultMessage.set(`❌ Failed to import CV: ${error?.message ?? 'Unknown error'}`);
}

  }
}

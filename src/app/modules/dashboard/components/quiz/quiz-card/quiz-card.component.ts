import { JsonPipe } from '@angular/common';
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-quiz-card',
  standalone: true,
  templateUrl: './quiz-card.component.html',
  styleUrls: ['./quiz-card.component.scss'],
  imports: [FormsModule, CommonModule],
})
export class QuizCardComponent {
  @Input() question: any;
  @Input() questionIndex!: number;
  @Input() totalQuestions!: number;
  @Input() result: any = null;
  @Input() answer: string = ''; // ✅ default value to prevent undefined

  @Output() answerChanged = new EventEmitter<string>();
  @Output() submitClicked = new EventEmitter<void>();
  @Output() nextClicked = new EventEmitter<void>();
  @Output() resultClicked = new EventEmitter<void>();

  onSubmit() {
    this.submitClicked.emit();
  }

  onNext() {
    this.nextClicked.emit();
  }

  handleEnter(event: Event) {
  event.preventDefault();  // prevent newline
  this.onSubmit();
}


  onInputChange(event: Event) {
    const textarea = event.target as HTMLTextAreaElement; // ✅ Correct element type
    const value = textarea.value;
    this.answerChanged.emit(value);
  }

  onViewResult() {
    this.resultClicked.emit();
  }
}

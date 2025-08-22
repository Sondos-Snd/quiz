import { CommonModule } from '@angular/common';
import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { QuizCardComponent } from '../quiz-card/quiz-card.component';
import { ActivatedRoute, Router } from '@angular/router';
import { QuestionService } from '../../../services/question.service';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-quiz',
  templateUrl: './quiz.component.html',
  standalone: true,
  imports: [QuizCardComponent, CommonModule, FormsModule],
})
export class QuizComponent implements OnInit {
  topic: string = '';
  questions: any[] = [];
  currentQuestionIndex = 0;
  currentQuestion: any;
  userAnswer = '';
  result: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private questionService: QuestionService
  ) {}

ngOnInit(): void {
  this.route.queryParams.subscribe(params => {
    this.topic = params['topic'] || ''; // if topic param exists, else empty string
    this.loadQuestions();
  });
}


loadQuestions() {
  let questionsObservable: Observable<any>;
  if (this.topic && this.topic.trim() !== '') {
    questionsObservable = this.questionService.getQuestions(this.topic);
  } else {
    questionsObservable = this.questionService.getAllQuestions();
  }

  questionsObservable.subscribe({
    next: (questions) => {
      this.questions = questions;
      this.currentQuestionIndex = 0;
      this.currentQuestion = this.questions.length ? this.questions[0] : null;
    },
    error: (err) => {
      console.error('Error loading questions:', err);
      this.questions = [];
      this.currentQuestion = null;
    }
  });
}


  onSubmitAnswer() {
    if (!this.currentQuestion) {
      return;
    }
    const answerRequest = {
      questionId: this.currentQuestion.id,
      userAnswer: this.userAnswer
    };

    this.questionService.checkAnswer(answerRequest).subscribe({
      next: (res) => {
        this.result = res;
      },
      error: (err) => {
        console.error('Error checking answer:', err);
        this.result = null;
      }
    });
  }

  onNextQuestion() {
    this.result = null;
    this.userAnswer = '';
    this.currentQuestionIndex++;
    if (this.currentQuestionIndex < this.questions.length) {
      this.currentQuestion = this.questions[this.currentQuestionIndex];
    } else {
      this.currentQuestion = null;
    }
  }

  onViewResult() {
    this.router.navigate(['/result']);
  }

  onUserAnswerChange(val: string) {
    this.userAnswer = val;
  }
}

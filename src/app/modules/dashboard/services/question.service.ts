// src/app/services/question.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.prod';


@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  private readonly baseUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  getQuestions(topic: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/questions/${topic}`);
  }

  getAllQuestions(): Observable<any> {
    return this.http.get(`${this.baseUrl}/questions`);
  }

  checkAnswer(answerRequest: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/answers/check`, answerRequest);
  }
}

// src/app/services/question.service.ts
@Injectable({ providedIn: 'root' })
export class QuestionService {
  private readonly baseUrl = 'https://quiz-1-pwdb.onrender.com/api'; // <- single slash

  constructor(private http: HttpClient) {}

  getQuestions(topic: string) {
    return this.http.get(`${this.baseUrl}/questions/${encodeURIComponent(topic)}`);
  }

  getAllQuestions() {
    return this.http.get(`${this.baseUrl}/questions`);
  }

  checkAnswer(answerRequest: any) {
    return this.http.post(`${this.baseUrl}/answers/check`, answerRequest);
  }
}

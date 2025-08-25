import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { Observable, forkJoin, of } from 'rxjs';
import { environment } from '../../../../../../environments/environment.prod';


export interface QuestionPayload {
  topic: string;
  questionText: string;
  correctAnswer: string;
}

interface CVUploadResponse {
  message: string;
  generatedQuestions?: number;
  extractedSkills?: string[];
}

@Injectable({
  providedIn: 'root',
})
export class QuestionService {
    // Upload CV and extract skills
  private readonly baseUrl = environment.apiBaseUrl;
  private readonly apiUrl = `${this.baseUrl}/admin/questions/add`;

  constructor(private http: HttpClient) {}

async addQuestion(question: QuestionPayload): Promise<{ id: string }> {
  try {
    return await firstValueFrom(this.http.post<{ id: string }>(this.apiUrl, question));
  } catch (error: any) {
    // Rethrow the full error including possible body
    throw error;
  }
}


  importCV(cvFile: File): Observable<CVUploadResponse> {
    const formData = new FormData();
    formData.append('cv', cvFile);

    return this.http.post<CVUploadResponse>(`${this.baseUrl}/cv/import`, formData);
  }

}

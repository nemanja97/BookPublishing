import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EditorChoices } from '../model/EditorChoices';
import { EditorNotes } from '../model/EditorNotes';
import { EditorReplacement } from '../model/EditorReplacement';
import { PlagiarismBlameForm } from '../model/PlagiarismBlameForm';
import { PlagiarismEditorVote } from '../model/PlagiarismEditorVote';
import { ApiService } from './api.service';
import { JwtService } from './jwt.service';
import { TaskService } from './task.service';

@Injectable({
  providedIn: 'root',
})
export class PlagiarismService {
  constructor(
    private apiService: ApiService,
    private jwtService: JwtService,
    private taskService: TaskService
  ) {}

  plagiarismReport(dto: PlagiarismBlameForm, taskId: string): Observable<any> {
    return this.taskService.submitFormUnformatted(dto, taskId);
  }

  selectEditorsToTakeNotes(
    dto: EditorChoices,
    taskId: string
  ): Observable<any> {
    return this.taskService.submitFormUnformatted(dto, taskId);
  }

  selectReplacementEditor(
    dto: EditorReplacement,
    taskId: string
  ): Observable<any> {
    return this.taskService.submitFormUnformatted(dto, taskId);
  }

  takeNotes(dto: EditorNotes, taskId: string): Observable<any> {
    return this.taskService.submitFormUnformatted(dto, taskId);
  }

  castVote(dto: PlagiarismEditorVote, taskId: string): Observable<any> {
    return this.taskService.submitFormUnformatted(dto, taskId);
  }

  createHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
    });
  }
}

import { Injectable } from '@angular/core';
import CamundaForm from 'src/app/model/CamundaForm';
import { ApiService } from './api.service';
import { StorageService } from './storage.service';
import { JwtService } from './jwt.service';
import { Observable } from 'rxjs';
import { TaskService } from './task.service';

@Injectable({
  providedIn: 'root',
})
export class FormsService {
  constructor(
    private apiService: ApiService,
    private jwtService: JwtService,
    private taskService: TaskService
  ) {}

  getWriterRegistrationForm(processId: string): any {
    return this.apiService.get(`/forms/users/writer_registration/${processId}`);
  }

  getWriterAssociationSubmissionForm(processId: string): any {
    const headers = this.jwtService.createAuthHeaders();
    return this.apiService.get(
      `/forms/users/writer_registration/work_submission/${processId}`,
      { headers }
    );
  }

  getWriterRegistrationEditorVoteForm(processId: string): any {
    const headers = this.jwtService.createAuthHeaders();
    return this.taskService.getForm(processId, 'Activity_0vfozig');
  }

  getReaderRegistrationForm(processId: string): Observable<CamundaForm> {
    return this.apiService.get(`/forms/users/reader_registration/${processId}`);
  }

  getBetaReaderRegistrationForm(processId: string): Observable<CamundaForm> {
    return this.apiService.get(
      `/forms/users/reader_registration/beta_reader/${processId}`
    );
  }

  getPlagiarismBlameForm(processId: string): Observable<CamundaForm> {
    return this.taskService.getForm(processId, 'plagiarismBlame');
  }

  getPlagiarismEditorChoicesForm(processId: string): Observable<CamundaForm> {
    return this.taskService.getForm(processId, 'chooseEditors');
  }

  getPlagiarismEditorReplacementForm(
    processId: string
  ): Observable<CamundaForm> {
    return this.taskService.getForm(processId, 'replacementEditor');
  }

  getPlagiarismEditorNotesForm(processId: string): Observable<CamundaForm> {
    return this.taskService.getForm(processId, 'makeNote');
  }

  getPlagiarismEditorVoteForm(processId: string): Observable<CamundaForm> {
    return this.taskService.getForm(processId, 'editor_vote');
  }
}

import { Injectable } from '@angular/core';
import { ApiService } from './api.service';
import { JwtService } from './jwt.service';
import { Observable } from 'rxjs';
import CamundaForm from '../model/CamundaForm';
import { FormFieldDTO } from '../model/FormFieldDTO';
import { HttpClient } from '@angular/common/http';
import { FormFieldListDTO } from '../model/FormFieldListDTO';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  constructor(
    private apiService: ApiService,
    private jwtService: JwtService,
    private http: HttpClient
  ) {}

  getTasksForCurrentUser(taskName?: string): any {
    const headers = this.jwtService.createAuthHeaders();
    console.log('headers' + headers);
    if (taskName) {
      return this.apiService.get(`/tasks?taskName=${taskName}`, {
        headers,
      });
    } else {
      return this.apiService.get(`/tasks`, {
        headers,
      });
    }
  }

  getForm(processId: string, taskKey: string): Observable<CamundaForm> {
    const headers = this.jwtService.createAuthHeaders();
    return this.apiService.get(
      `/tasks/form?processId=${processId}&taskKey=${taskKey}`,
      { headers }
    );
  }

  getFormUnAuth(processId: string, taskKey: string): Observable<CamundaForm> {
    return this.apiService.get(
      `/tasks/form?processId=${processId}&taskKey=${taskKey}`
    );
  }

  submitForm(formFields: Array<FormFieldDTO>, taskId: string): Observable<any> {
    const formData: FormData = new FormData();
    const formList = new FormFieldListDTO(formFields);
    formData.append('form', JSON.stringify(formList));
    return this.http.post(
      `http://localhost:8080/api/tasks/form/${taskId}`,
      formData
    );
  }

  submitFormUnformatted(dto: any, taskId: string): Observable<any> {
    const formData: FormData = new FormData();
    const formList = this.dtoConversionHelper(dto);
    formData.append('form', JSON.stringify(formList));
    return this.http.post(
      `http://localhost:8080/api/tasks/form/${taskId}`,
      formData
    );
  }

  submitFiles(dto: any, taskId: string): Observable<any> {
    const formData: FormData = new FormData();
    console.log(dto);
    for (const file of dto.files) {
      formData.append('files', file);
    }
    return this.http.post<any>(
      `http://localhost:8080/api/tasks/form/${taskId}`,
      formData
    );
  }

  dtoConversionHelper(dto: any): FormFieldListDTO {
    let retVal = new FormFieldListDTO([]);

    for (const [key, val] of Object.entries(dto)) {
      retVal.dtoList.push({ fieldId: key, fieldValue: val });
    }

    return retVal;
  }
}

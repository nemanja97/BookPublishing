import {Injectable} from '@angular/core';
import {HttpHeaders} from '@angular/common/http';
import {BehaviorSubject, Observable, ReplaySubject} from 'rxjs';

import {ApiService} from './api.service';
import {JwtService} from './jwt.service';
import {distinctUntilChanged} from 'rxjs/operators';
import {Role} from 'src/app/model/enum/Roles';
import {CreateUser} from 'src/app/model/CreateUser';
import {CreateBetaReader} from 'src/app/model/CreateBetaReader';
import {Credentials} from 'src/app/model/Credentials';
import {FormFieldListDTO} from '../model/FormFieldListDTO';
import {FormFieldDTO} from '../model/FormFieldDTO';
import {TaskService} from './task.service';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  private currentUserRoleSubject = new BehaviorSubject<Role>({} as Role);
  public currentUserRole = this.currentUserRoleSubject
    .asObservable()
    .pipe(distinctUntilChanged());

  private isAuthenticatedSubject = new ReplaySubject<boolean>(0);
  public isAuthenticated = this.isAuthenticatedSubject.asObservable();

  constructor(private apiService: ApiService, private jwtService: JwtService,
              private taskService: TaskService) {
  }

  // Verify JWT in local storage with server & load user's info.
  // This runs once on application startup.
  populate() {
    // If JWT detected, attempt to get & store user's info
    if (this.jwtService.getToken()) {
      this.setAuth(this.jwtService.getToken());
    } else {
      // Remove any potential remnants of previous auth states
      this.purgeAuth();
    }
  }

  setAuth(token: string) {
    // Save JWT sent from server in local storage
    this.jwtService.saveToken(token);
    // Set current user role into observable
    const decodedToken = this.jwtService.decodeToken(token);
    console.log(decodedToken);
    switch (decodedToken.role) {
      case 'ROLE_HEAD_EDITOR':
        this.currentUserRoleSubject.next(Role.HEAD_EDITOR);
        break;
      case 'ROLE_EDITOR':
        this.currentUserRoleSubject.next(Role.EDITOR);
        break;
      case 'ROLE_WRITER':
        this.currentUserRoleSubject.next(Role.WRITER);
        break;
      case 'ROLE_READER':
        this.currentUserRoleSubject.next(Role.READER);
        break;
      case 'ROLE_LECTOR':
        this.currentUserRoleSubject.next(Role.LECTOR);
        break;
      default:
        throw new Error('Unknown role');
    }
    // Set isAuthenticated to true
    this.isAuthenticatedSubject.next(true);
  }

  purgeAuth() {
    // Remove JWT from local storage
    this.jwtService.destroyToken();
    // Set current user to an empty object
    this.currentUserRoleSubject.next(Role.UNREGISTERED as Role);
    // Set auth status to false
    this.isAuthenticatedSubject.next(false);
  }

  registerWriter(userDTO: CreateUser, taskId: string): Observable<any> {
    const headers = this.createHeaders();
    const fieldList = new FormFieldListDTO(new Array<FormFieldDTO>());
    fieldList.dtoList.push(new FormFieldDTO('name', userDTO.name));
    fieldList.dtoList.push(new FormFieldDTO('surname', userDTO.surname));
    fieldList.dtoList.push(new FormFieldDTO('username', userDTO.username));
    fieldList.dtoList.push(new FormFieldDTO('email', userDTO.email));
    fieldList.dtoList.push(new FormFieldDTO('password', userDTO.password));
    fieldList.dtoList.push(new FormFieldDTO('city', userDTO.city));
    fieldList.dtoList.push(new FormFieldDTO('country', userDTO.country));
    fieldList.dtoList.push(new FormFieldDTO('genres', userDTO.genres));
    fieldList.dtoList.push(new FormFieldDTO('betaReader', userDTO.betaReader));

    return this.taskService.submitForm(fieldList.dtoList, taskId);
  }

  castEditorVote(dto: any, taskId: string): Observable<any> {
    const headers = this.jwtService.createAuthHeaders();
    const formFields = this.dtoConversionHelper(dto);
    return this.taskService.submitForm(formFields.dtoList, taskId);
  }

  dtoConversionHelper(dto: any): FormFieldListDTO {
    let retVal = new FormFieldListDTO([]);

    for (const [key, val] of Object.entries(dto)) {
      retVal.dtoList.push({fieldId: key, fieldValue: val});
    }

    return retVal;
  }

  addWriterAssociationRegisterWork(dto: any, taskId: String): Observable<any> {
    let formData: FormData = new FormData();
    for (var i = 0; i < dto.books.length; i++) {
      formData.append('books', dto.books[i]);
    }

    const headers = this.jwtService.createAuthHeadersWithoutContentType();
    return this.apiService.customPost(
      `/users/writer_registration/work_submission/${taskId}`,
      formData,
      {headers}
    );
  }

  registerReader(userDTO: CreateUser, taskId: string): Observable<any> {
    const headers = this.createHeaders();

    const fieldList = new FormFieldListDTO(new Array<FormFieldDTO>());
    fieldList.dtoList.push(new FormFieldDTO('name', userDTO.name));
    fieldList.dtoList.push(new FormFieldDTO('surname', userDTO.surname));
    fieldList.dtoList.push(new FormFieldDTO('username', userDTO.username));
    fieldList.dtoList.push(new FormFieldDTO('email', userDTO.email));
    fieldList.dtoList.push(new FormFieldDTO('password', userDTO.password));
    fieldList.dtoList.push(new FormFieldDTO('city', userDTO.city));
    fieldList.dtoList.push(new FormFieldDTO('country', userDTO.country));
    fieldList.dtoList.push(new FormFieldDTO('genres', userDTO.genres));
    fieldList.dtoList.push(new FormFieldDTO('betaReader', userDTO.betaReader));

    return this.taskService.submitForm(fieldList.dtoList, taskId);
  }

  registerBetaReader(
    userDTO: CreateBetaReader,
    taskId: string
  ): Observable<any> {
    const headers = this.createHeaders();
    const fieldList = new FormFieldListDTO(new Array<FormFieldDTO>());
    fieldList.dtoList.push(new FormFieldDTO('betaGenres', userDTO.betaGenres));
    return this.taskService.submitForm(fieldList.dtoList, taskId);
  }

  getWriterMembershipProcess(): Observable<any> {
    const headers = this.jwtService.createAuthHeaders();
    return this.apiService.get(`/users/writer_registration/membership`, {
      headers,
    });
  }

  verifyRegistration(processId: String): Observable<any> {
    return this.apiService.get(`/users/verify/${processId}`);
  }

  attemptAuth(credentials: Credentials): Observable<any> {
    const headers = this.createHeaders();
    const httpOptions = {
      headers,
      responseType: 'text',
    };
    return this.apiService.post('/auth', credentials, httpOptions);
  }

  createHeaders(): HttpHeaders {
    return new HttpHeaders({
      'Content-Type': 'application/json',
    });
  }

  getCurrentUserRole(): Role {
    return this.currentUserRoleSubject.value;
  }
}

import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, ReplaySubject } from 'rxjs';
import { distinctUntilChanged } from 'rxjs/operators';
import { Role } from 'src/app/model/enum/Roles';
import { ApiService } from './api.service';
import { JwtService } from './jwt.service';

@Injectable({
  providedIn: 'root',
})
export class ProcessService {
  private currentUserRoleSubject = new BehaviorSubject<Role>({} as Role);
  public currentUserRole = this.currentUserRoleSubject
    .asObservable()
    .pipe(distinctUntilChanged());

  private isAuthenticatedSubject = new ReplaySubject<boolean>(0);
  public isAuthenticated = this.isAuthenticatedSubject.asObservable();

  constructor(private apiService: ApiService, private jwtService: JwtService) {}

  startWriterRegistration(): Observable<any> {
    return this.apiService.get(`/process/writer_registration`, {
      responseType: 'text',
    });
  }

  startReaderRegistration(): Observable<any> {
    return this.apiService.get(`/process/reader_registration`, {
      responseType: 'text',
    });
  }

  startBookPublishing(): Observable<any> {
    return this.apiService.get(`/book_process/book_publishing`, {
      responseType: 'text',
    });
  }

  startPlagiarismBlameProcess(): Observable<any> {
    return this.startProcess('plagiarism_blame');
  }

  startWriterAssociationMembership(): Observable<any> {
    const headers = this.jwtService.createAuthHeaders();
    return this.apiService.get(`/process/writer_membership_request`, {
      headers,
      responseType: 'text',
    });
  }

  getProcessForBook(bookId: string): Observable<any> {
    return this.apiService.get(`/book_process/process_for_book/${bookId}`, {
      responseType: 'text',
    });
  }

  startProcess(key: string): Observable<any> {
    const headers = this.jwtService.createAuthHeaders();
    return this.apiService.get(`/process?key=${key}`, {
      headers,
      responseType: 'text',
    });
  }

  startProcessReg(key: string): Observable<any> {
    return this.startProcess(key);
    return this.apiService.get(`/process/reg?key=${key}`, {
      responseType: 'text',
    });
  }
}

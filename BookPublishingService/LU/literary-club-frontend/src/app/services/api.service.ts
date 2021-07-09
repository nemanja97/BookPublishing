import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient, HttpParams, HttpHeaders } from '@angular/common/http';
import { Observable ,  throwError } from 'rxjs';

import { catchError } from 'rxjs/operators';
import { JwtService } from './jwt.service';


@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(
    private http: HttpClient,
    private jwtService: JwtService
  ) {}

  private formatErrors(error: any) {
    return throwError(error.error);
  }

  get(path: string, httpOptions?: any): Observable<any> {
    return this.http.get(
      `${environment.luApi}${path}`,
      httpOptions,
       ).pipe(catchError(this.formatErrors));
  }

  put(path: string, body: object = {}, httpOptions?: any): Observable<any> {
    return this.http.put(
      `${environment.luApi}${path}`,
      JSON.stringify(body),
      httpOptions
    ).pipe(catchError(this.formatErrors));
  }

  post(path: string, body: object = {}, httpOptions?: any): Observable<any> {
    return this.http.post(
      `${environment.luApi}${path}`,
      JSON.stringify(body),
      httpOptions,
    ).pipe(catchError(this.formatErrors));
  }

  customPost(path: string, body: object = {}, httpOptions?: any): Observable<any> {
    return this.http.post(
      `${environment.luApi}${path}`,
      body,
      httpOptions,
    ).pipe(catchError(this.formatErrors));
  }

  delete(path: string, httpOptions?: any): Observable<any> {
    return this.http.delete(
      `${environment.luApi}${path}`,
       httpOptions,
    ).pipe(catchError(this.formatErrors));
  }

  deleteWithPayload(path: string, body: object = {}, httpOptions?: any): Observable<any> {
    return this.http.request(
      'delete',
      `${environment.luApi}${path}`,
      {body, headers: httpOptions}
      ).pipe(catchError(this.formatErrors));
  }

}

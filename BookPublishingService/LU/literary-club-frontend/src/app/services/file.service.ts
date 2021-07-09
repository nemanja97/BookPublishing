import {HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ApiService} from './api.service';
import {JwtService} from './jwt.service';

@Injectable({
  providedIn: 'root',
})
export class FileService {
  constructor(private apiService: ApiService, private jwtService: JwtService) {
  }

  getWriterAssociationSubmittedWorks(processId: String): Observable<any> {
    const headers = this.jwtService.createAuthHeadersWithoutContentType();
    return this.apiService.get(
      `/files/writer_association_submissions/${processId}`,
      {responseType: 'blob' as 'json', headers}
    );
  }

  getPossiblePlagiarizedWorks(processId: String): Observable<any> {
    const headers = this.jwtService.createAuthHeadersWithoutContentType();
    return this.apiService.get(
      `/files/plagiarism/${processId}`,
      {responseType: 'blob' as 'json', headers}
    );
  }

  getBookAsPdf(bookId: string): Observable<any> {
    const headers = this.jwtService.createAuthHeadersWithoutContentType();
    return this.apiService.get(
      `/files/getBookAsPdf/${bookId}`,{responseType: 'blob' as 'json', headers});
  }


  createHeaders(): HttpHeaders {
    return new HttpHeaders({
      'X-Auth-Token': this.jwtService.getToken()
    });
  }
}

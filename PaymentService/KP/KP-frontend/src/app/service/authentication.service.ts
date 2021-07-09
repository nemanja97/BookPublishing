import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LoginInfo} from '../model/LoginInfo';
import {Observable} from 'rxjs';
import {environment} from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) {
  }

  authentication(loginInfo: LoginInfo): Observable<string> {
    return this.http.post<string>(`${environment.kpApi}/auth`, loginInfo);
  }
}

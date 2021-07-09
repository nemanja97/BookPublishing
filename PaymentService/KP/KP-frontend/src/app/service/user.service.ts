import {Injectable} from '@angular/core';
import {UserRegistration} from '../model/UserRegistration';
import {HttpClient} from '@angular/common/http';
import {environment} from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  storeAdminRegistration(userRegistration: UserRegistration) {
    return this.http.post(`${environment.kpApi}/users/register`, userRegistration);
  }
}

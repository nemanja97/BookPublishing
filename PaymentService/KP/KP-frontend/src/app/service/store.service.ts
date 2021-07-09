import { Injectable } from '@angular/core';
import {UserRegistration} from '../model/UserRegistration';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {StoreRegistration} from '../model/StoreRegistration';

@Injectable({
  providedIn: 'root'
})
export class StoreService {

  constructor(private http: HttpClient) { }

  storeAdminRegistration(storeRegistration: StoreRegistration) {
    return this.http.post(`${environment.kpApi}/store/register`, storeRegistration);
  }
}

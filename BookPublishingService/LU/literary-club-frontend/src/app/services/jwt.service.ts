import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root',
})
export class JwtService {
  private helper = new JwtHelperService();

  constructor(private storageService: StorageService) {}

  decodeToken(token: string) {
    return this.helper.decodeToken(token);
  }

  getToken(): any {
    return this.storageService.get('jwtToken');
  }

  saveToken(token: string) {
    this.storageService.set('jwtToken', token);
  }

  destroyToken() {
    this.storageService.delete('jwtToken');
  }

  createAuthHeaders(): HttpHeaders {
    if (this.getToken()) {
      return new HttpHeaders({
        'Content-Type': 'application/json',
        'X-Auth-Token': this.getToken(),
      });
    } else {
      return new HttpHeaders({
        'Content-Type': 'application/json',
      });
    }
  }

  createAuthHeadersWithoutContentType(): HttpHeaders {
    return new HttpHeaders({
      'X-Auth-Token': this.getToken(),
    });
  }
}

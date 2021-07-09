import {Injectable} from '@angular/core';
import {HttpInterceptor} from '@angular/common/http';
import {HttpRequest} from '@angular/common/http';
import {HttpHandler} from '@angular/common/http';
import {Observable} from 'rxjs';
import {HttpEvent, HttpHeaders} from '@angular/common/http';
import {Injector} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TokenInterceptorService implements HttpInterceptor {

  constructor(private inj: Injector) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.getToken();
    console.log('token' + token);
    if (token) {
      request = request.clone({
        setHeaders: {
          'X-Auth-Token': `${this.getToken()}`
        }
      });
    } else {
      request = request.clone();
    }


    return next.handle(request);
  }

  getToken(): string {
    const token = localStorage.getItem('jwtToken');
    if (!token) {
      return '';
    }
    return token;
  }
}

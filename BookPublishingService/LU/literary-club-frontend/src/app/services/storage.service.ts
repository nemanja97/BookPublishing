import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  get(key: string): any  {
    return window.localStorage.getItem(key);
  }

  set(key: string, value: string) {
    window.localStorage.setItem(key, value);
  }

  delete(key: string) {
    window.localStorage.removeItem(key);
  }
}

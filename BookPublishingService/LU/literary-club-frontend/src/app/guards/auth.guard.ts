import { Injectable } from '@angular/core';
import {
  CanActivate,
  CanActivateChild,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree,
  Router,
} from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from '../services/user.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate, CanActivateChild {
  constructor(private userService: UserService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    if (this.userService.isAuthenticated) {
      const currentRole = this.userService.getCurrentUserRole();
      if (next.data.roles && next.data.roles.indexOf(currentRole) === -1) {
        console.log("roles",next.data.roles);
        this.router.navigate(['/']);
        return false;
      }
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }

  canActivateChild(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    if (this.userService.isAuthenticated) {
      const currentRole = this.userService.getCurrentUserRole();
      if (next.data.roles && next.data.roles.indexOf(currentRole) === -1) {
        this.router.navigate(['/']);
        return false;
      }
      return true;
    } else {
      this.router.navigate(['/login']);
      return false;
    }
  }
}

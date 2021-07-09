import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Credentials } from 'src/app/model/Credentials';
import { Role } from 'src/app/model/enum/Roles';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginInfo: Credentials;

  constructor(
    private snackBar: MatSnackBar,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loginInfo = {
      email: '',
      password: '',
    };
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, 'Close', {
      duration: 2000,
    });
  }

  isEmpty(str: string): boolean {
    return !str || str.trim() === '';
  }

  validate(): boolean {
    if (this.isEmpty(this.loginInfo.email)) {
      this.openSnackBar('Email must be entered');
      return false;
    }
    if (this.isEmpty(this.loginInfo.password)) {
      this.openSnackBar('Password must be entered');
      return false;
    }
    return true;
  }

  login() {
    if (!this.validate()) {
      return;
    }
    this.userService.attemptAuth(this.loginInfo).subscribe(
      (token: any) => {
        this.userService.setAuth(token);
        if (this.userService.getCurrentUserRole() === Role.HEAD_EDITOR) {
          this.router.navigate(['/home']);
        }
        if (this.userService.getCurrentUserRole() === Role.EDITOR) {
          this.router.navigate(['/home']);
        } else if (this.userService.getCurrentUserRole() === Role.WRITER) {
          this.router.navigate(['/home']);
        } else if (this.userService.getCurrentUserRole() === Role.READER) {
          this.router.navigate(['/home']);
        } else if (this.userService.getCurrentUserRole() === Role.LECTOR) {
          this.router.navigate(['/home']);
        }
      },
      (error: any) =>
        this.openSnackBar('Login failed; Invalid user ID or password')
    );
  }
}

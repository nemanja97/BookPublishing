import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import {Router} from '@angular/router';
import {LoginInfo} from '../model/LoginInfo';
import {AuthenticationService} from '../service/authentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private router: Router, private authenticationService: AuthenticationService, private snackBar: MatSnackBar) {
  }

  loginForm: FormGroup;
  hide = true;

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  get email() {
    return this.loginForm.controls.email.value as string;
  }

  get password() {
    return this.loginForm.controls.password.value as string;
  }

  onLogInSubmit() {
    const loginInfo = new LoginInfo(this.email, this.password);
    this.authenticationService.authentication(loginInfo).subscribe(
      response => {
        console.log(response);
        localStorage.setItem('token', response);
        this.router.navigateByUrl('dashboard');
      },
      error => {
        if (error.status === 200) {
          const r = error.error.text;
          console.log(r);
          localStorage.setItem('token', r);
          this.router.navigateByUrl('dashboard');
        } else {
          this.snackBar.open('Error logging in');
        }
      }
    );
  }

}

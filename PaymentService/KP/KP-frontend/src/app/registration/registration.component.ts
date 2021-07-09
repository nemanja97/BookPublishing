import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from '../service/user.service';
import {UserRegistration} from '../model/UserRegistration';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UserService, private snackBar: MatSnackBar) {
  }

  registrationForm: FormGroup;
  hide = true;

  ngOnInit() {
    this.registrationForm = this.formBuilder.group({
      email: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  get email() {
    return this.registrationForm.controls.email.value as string;
  }

  get password() {
    return this.registrationForm.controls.password.value as string;
  }

  onRegistrationSubmit() {
    const registrationDto = new UserRegistration(this.email, this.password);
    this.userService.storeAdminRegistration(registrationDto).subscribe(
      response => {
        console.log(response);
        this.router.navigateByUrl('login');
      },
      error => {
        this.snackBar.open('Error registering');
      }
    );
  }
}

import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  loggedIn = true;

  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  login() {
    this.router.navigateByUrl('login');
  }

  register() {
    this.router.navigateByUrl('register');
  }

  dashboard() {
    this.router.navigateByUrl('dashboard');
  }

  isLoggedIn() {

  }

}

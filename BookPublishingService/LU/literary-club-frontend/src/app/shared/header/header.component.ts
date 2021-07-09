import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Role } from 'src/app/model/enum/Roles';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  constructor(private router: Router, private userService: UserService) {}

  currentUserRole: Role;

  ngOnInit() {
    this.userService.currentUserRole.subscribe((role) => {
      this.currentUserRole = role;
    });
  }

  logout() {
    this.userService.purgeAuth();
  }
}

import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'literary-club-frontend';

  constructor(
    private userService: UserService
  ) {}

  ngOnInit() {
    this.userService.populate();
  }

}

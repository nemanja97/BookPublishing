import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-email-verification',
  templateUrl: './email-verification.component.html',
  styleUrls: ['./email-verification.component.scss']
})
export class EmailVerificationComponent implements OnInit {

  public loadingData: string = "loading";

  constructor(
    private activatedRoute: ActivatedRoute,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.loadingData = "loading";
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        const id = params.get('id');
        if (id == null) {
          this.loadingData = "failure";
        } else {
          this.userService.verifyRegistration(id).subscribe(
            () => {this.loadingData = "success";},
            () => {this.loadingData = "failure";}
          )
        }
      }
    );
  }

}

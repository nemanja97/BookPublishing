import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, ParamMap } from '@angular/router';
import CamundaForm from '../model/CamundaForm';
import CamundaFormItem from '../model/CamundaFormItem';
import { FormsService } from '../services/forms.service';
import { UserService } from '../services/user.service';
import {TaskService} from '../services/task.service';

@Component({
  selector: 'app-vote-on-writer-membership-application',
  templateUrl: './vote-on-writer-membership-application.component.html',
  styleUrls: ['./vote-on-writer-membership-application.component.scss']
})
export class VoteOnWriterMembershipApplicationComponent implements OnInit {

  public formFields: CamundaFormItem[] = [];
  public loadingData: boolean = true;
  private processInstanceId: string = '';
  private taskId: string = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private formService: FormsService,
    private snackBar: MatSnackBar,
    private taskService: TaskService
  ) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        const id = params.get('processId');
        if (id == null) {
          this.loadingData = true;
        } else {
          this.loadingData = true;
          this.formService.getWriterRegistrationEditorVoteForm(id).subscribe(
            (res: CamundaForm) => {
              this.processInstanceId = res.processInstanceId;
              this.taskId = res.taskId;
              this.formFields = res.formFields;
              this.formFields[0].properties.choices = this.formFields[0].properties.choices?.split(
                ','
              );
              console.log(res);
              this.loadingData = false;
            },
            (err: any) => {
              console.error(err);
              this.loadingData = false;
            }
          )
        }
      }
    );
  }

  castVote(fieldValues: any) {
    console.log(fieldValues);
    this.userService.castEditorVote(fieldValues, this.taskId).subscribe(
      () => {
        this.openSnackBar('Vote successfully cast.');
      },
      () => {
        this.openSnackBar('Failed to cast vote.');
      }
    );
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, 'Close', {
      horizontalPosition: "center",
      verticalPosition: "top",
    });
  }

}

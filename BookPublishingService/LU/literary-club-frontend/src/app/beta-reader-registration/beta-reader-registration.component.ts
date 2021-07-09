import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import CamundaForm from '../model/CamundaForm';
import CamundaFormItem from '../model/CamundaFormItem';
import { CreateBetaReader } from '../model/CreateBetaReader';
import { FormsService } from '../services/forms.service';
import { StorageService } from '../services/storage.service';
import { UserService } from '../services/user.service';
import {TaskService} from '../services/task.service';

@Component({
  selector: 'app-beta-reader-registration',
  templateUrl: './beta-reader-registration.component.html',
  styleUrls: ['./beta-reader-registration.component.scss']
})
export class BetaReaderRegistrationComponent implements OnInit {

  public formFields: CamundaFormItem[] = [];
  public loadingData: boolean = true;
  private taskId: string = '';

  constructor(
    private formsService: FormsService,
    private storageService: StorageService,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private router: Router,
    private taskService: TaskService
  ) {}

  ngOnInit(): void {
    this.loadingData = true;

    let processId = this.storageService.get('readerRegistrationProcessId');
    if (processId == null) {
      this.router.navigate(['/signUp/reader']);
    } else {
      this.getFormForExistingProcess(processId);
    }
  }

  private getFormForExistingProcess(processId: any) {
    this.taskService.getFormUnAuth(processId,'betaReaderGenreChoices').subscribe(
      (res: CamundaForm) => {
        console.log(res);
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
    );
  }

  registerUser(fieldValues: CreateBetaReader) {
    console.log(fieldValues);
    this.userService.registerBetaReader(fieldValues, this.taskId).subscribe(
      () => {
        this.openSnackBar('Registration success. Please check your inbox for verification.');
      },
      () => {
        this.openSnackBar('Registration failure.');
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

import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import CamundaForm from '../model/CamundaForm';
import CamundaFormItem from '../model/CamundaFormItem';
import { CreateUser } from '../model/CreateUser';
import { FormsService } from '../services/forms.service';
import { ProcessService } from '../services/process.service';
import { StorageService } from '../services/storage.service';
import { UserService } from '../services/user.service';
import { TaskService } from '../services/task.service';

@Component({
  selector: 'app-writer-registration',
  templateUrl: './writer-registration.component.html',
  styleUrls: ['./writer-registration.component.scss'],
})
export class WriterRegistrationComponent implements OnInit {
  public formFields: CamundaFormItem[] = [];
  public loadingData: boolean = true;
  private processInstanceId: string = '';
  private taskId: string = '';

  constructor(
    private formsService: FormsService,
    private storageService: StorageService,
    private processService: ProcessService,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private taskService: TaskService
  ) {}

  ngOnInit(): void {
    this.loadingData = true;

    let processId = this.storageService.get('writerRegistrationProcessId');
    if (processId === null) {
      processId = this.startNewProcessAndGetForm(processId);
    } else {
      this.getFormForExistingProcess(processId);
    }
  }

  private getFormForExistingProcess(processId: any) {
    this.taskService.getFormUnAuth(processId, 'Activity_1rctvdx').subscribe(
      (res: CamundaForm) => {
        this.processInstanceId = res.processInstanceId;
        this.taskId = res.taskId;
        this.formFields = res.formFields;
        this.formFields[7].properties.choices = this.formFields[7].properties.choices?.split(
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

  private startNewProcessAndGetForm(processId: any) {
    this.processService.startProcessReg('writer_registration').subscribe(
      (res) => {
        processId = res;
        this.storageService.set('writerRegistrationProcessId', res);
        this.taskService.getFormUnAuth(processId, 'Activity_1rctvdx').subscribe(
          (res: CamundaForm) => {
            this.processInstanceId = res.processInstanceId;
            this.taskId = res.taskId;
            this.formFields = res.formFields;
            this.formFields[7].properties.choices = this.formFields[7].properties.choices?.split(
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
      },
      (err: any) => {
        throw new Error(
          `Could not start a new writer registration process - ${err}`
        );
      }
    );
    return processId;
  }

  registerUser(fieldValues: CreateUser) {
    console.log(fieldValues);
    this.userService.registerWriter(fieldValues, this.taskId).subscribe(
      () => {
        this.openSnackBar(
          'Registration success. Please check your inbox for verification.'
        );
      },
      () => {
        this.openSnackBar('Registration failure.');
      }
    );
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
}

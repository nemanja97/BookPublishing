import {Component, OnInit} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';
import CamundaForm from '../model/CamundaForm';
import CamundaFormItem from '../model/CamundaFormItem';
import {FormsService} from '../services/forms.service';
import {ProcessService} from '../services/process.service';
import {StorageService} from '../services/storage.service';
import {UserService} from '../services/user.service';
import {TaskService} from '../services/task.service';

@Component({
  selector: 'app-writer-association-registration',
  templateUrl: './writer-association-registration.component.html',
  styleUrls: ['./writer-association-registration.component.scss'],
})
export class WriterAssociationRegistrationComponent implements OnInit {
  public formFields: CamundaFormItem[] = [];
  public loadingData: boolean = true;
  public submission: any = undefined;
  private processInstanceId: string = '';
  private taskId: string = '';

  constructor(
    private formsService: FormsService,
    private storageService: StorageService,
    private processService: ProcessService,
    private userService: UserService,
    private snackBar: MatSnackBar,
    private taskService: TaskService
  ) {
  }

  ngOnInit(): void {
    this.loadingData = true;

    this.userService.getWriterMembershipProcess().subscribe(
      (res) => {
        console.log(res);
        this.submission = res;

        if (this.submission == null) {
          let processId = this.storageService.get(
            'writerAssociationRegistrationProcessId'
          );
          if (processId === null) {
            processId = this.startNewProcessAndGetForm(processId);
          } else {
            this.getFormForExistingProcess(processId);
          }
        }
      },
      (err) => {
        console.error(err);
        this.submission = null;

        let processId = this.storageService.get(
          'writerAssociationRegistrationProcessId'
        );
        if (processId === null) {
          processId = this.startNewProcessAndGetForm(processId);
        } else {
          this.getFormForExistingProcess(processId);
        }
      }
    );
  }

  submitWork(fieldValues: any) {
    console.log(fieldValues);
    this.taskService.submitFiles(fieldValues, this.taskId)
      .subscribe(
        () => {
          this.openSnackBar('Work successfully submitted.');
        },
        () => {
          this.openSnackBar('Error submitting work.');
        }
      );
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  private getFormForExistingProcess(processId: any) {
    this.taskService.getForm(processId, 'Activity_1keokrv').subscribe(
      (res: CamundaForm) => {
        this.processInstanceId = res.processInstanceId;
        this.taskId = res.taskId;
        this.formFields = res.formFields;
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
    this.processService.startProcess('writer_membership_request').subscribe(
      (res) => {
        processId = res;
        this.storageService.set('writerAssociationRegistrationProcessId', res);
        this.taskService.getForm(processId, 'Activity_1keokrv')
          .subscribe(
            (res: CamundaForm) => {
              this.processInstanceId = res.processInstanceId;
              this.taskId = res.taskId;
              this.formFields = res.formFields;
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
}

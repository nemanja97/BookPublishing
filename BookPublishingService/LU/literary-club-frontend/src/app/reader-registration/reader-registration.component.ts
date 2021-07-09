import {Component, OnInit} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ActivatedRoute, Router} from '@angular/router';
import CamundaForm from '../model/CamundaForm';
import CamundaFormItem from '../model/CamundaFormItem';
import {CreateUser} from '../model/CreateUser';
import {FormsService} from '../services/forms.service';
import {ProcessService} from '../services/process.service';
import {StorageService} from '../services/storage.service';
import {UserService} from '../services/user.service';
import {TaskService} from '../services/task.service';

@Component({
  selector: 'app-reader-registration',
  templateUrl: './reader-registration.component.html',
  styleUrls: ['./reader-registration.component.scss'],
})
export class ReaderRegistrationComponent implements OnInit {
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
    private route: ActivatedRoute,
    private router: Router,
    private taskService: TaskService
  ) {
  }

  ngOnInit(): void {
    this.loadingData = true;

    let processId = this.storageService.get('readerRegistrationProcessId');
    console.log(processId);
    if (processId === null) {
      processId = this.startNewProcessAndGetForm(processId);
    } else {
      this.getFormForExistingProcess(processId);
    }
  }

  registerUser(fieldValues: CreateUser) {
    console.log(fieldValues);
    this.userService.registerReader(fieldValues, this.taskId).subscribe(
      () => {
        console.log('here');
        if (fieldValues.betaReader) {
          console.log('here');
          this.router.navigate(['beta'], {relativeTo: this.route});
        } else {
          this.openSnackBar(
            'Registration success. Please check your inbox for verification.'
          );
        }
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

  private getFormForExistingProcess(processId: any) {
    this.taskService.getFormUnAuth(processId, 'formInput').subscribe(
      (res: CamundaForm) => {
        console.log(res);
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
    this.processService.startProcessReg('reader_registration').subscribe(
      (res) => {
        processId = res;
        this.storageService.set('readerRegistrationProcessId', res);
        this.taskService.getFormUnAuth(processId, 'formInput').subscribe(
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
          `Could not start a new reader registration process - ${err}`
        );
      }
    );
    return processId;
  }
}

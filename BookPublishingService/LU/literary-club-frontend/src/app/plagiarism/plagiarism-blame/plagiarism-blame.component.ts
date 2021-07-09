import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import CamundaForm from 'src/app/model/CamundaForm';
import CamundaFormItem from 'src/app/model/CamundaFormItem';
import { FormsService } from 'src/app/services/forms.service';
import { PlagiarismService } from 'src/app/services/plagiarism.service';
import { ProcessService } from 'src/app/services/process.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-plagiarism-blame',
  templateUrl: './plagiarism-blame.component.html',
  styleUrls: ['./plagiarism-blame.component.scss']
})
export class PlagiarismBlameComponent implements OnInit {

  public formFields: CamundaFormItem[] = [];
  public loadingData: boolean = true;
  public submission: any = undefined;
  private processInstanceId: string = '';
  private taskId: string = '';

  constructor(
    private formsService: FormsService,
    private storageService: StorageService,
    private processService: ProcessService,
    private plagiarismService: PlagiarismService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.loadingData = true;

    let processId = this.storageService.get('plagiarismBlameProcessId');
    if (processId === null) {
      processId = this.startNewProcessAndGetForm(processId);
    } else {
      this.getFormForExistingProcess(processId);
    }
  }

  private getFormForExistingProcess(processId: any) {
    this.formsService.getPlagiarismBlameForm(processId).subscribe(
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
    this.processService.startPlagiarismBlameProcess().subscribe(
      (res) => {
        processId = res;
        this.storageService.set('plagiarismBlameProcessId', res);
        this.formsService
          .getPlagiarismBlameForm(processId)
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
          `Could not start a new plagiarism blame process - ${err}`
        );
      }
    );
    return processId;
  }

  submitReport(fieldValues: any) {
    console.log(fieldValues);
    this.plagiarismService
      .plagiarismReport(fieldValues, this.taskId)
      .subscribe(
        () => {
          this.openSnackBar('Report successfully submitted.');
        },
        () => {
          this.openSnackBar('Error submitting report.');
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

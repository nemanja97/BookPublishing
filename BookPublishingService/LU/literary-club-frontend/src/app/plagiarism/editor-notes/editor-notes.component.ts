import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, ParamMap } from '@angular/router';
import CamundaForm from 'src/app/model/CamundaForm';
import CamundaFormItem from 'src/app/model/CamundaFormItem';
import { FormsService } from 'src/app/services/forms.service';
import { PlagiarismService } from 'src/app/services/plagiarism.service';

@Component({
  selector: 'app-editor-notes',
  templateUrl: './editor-notes.component.html',
  styleUrls: ['./editor-notes.component.scss']
})
export class EditorNotesComponent implements OnInit {

  public formFields: CamundaFormItem[] = [];
  public loadingData: boolean = true;
  private processInstanceId: string = '';
  private taskId: string = '';

  constructor(
    private activatedRoute: ActivatedRoute,
    private plagiarismService: PlagiarismService,
    private formService: FormsService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(
      (params: ParamMap) => {
        const id = params.get('processId');
        if (id == null) {
          this.loadingData = true;
        } else {
          this.loadingData = true;
          this.formService.getPlagiarismEditorNotesForm(id).subscribe(
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
          )
        }
      }
    );
  }

  submitNotes(fieldValues: any) {
    console.log(fieldValues);
    this.plagiarismService.takeNotes(fieldValues, this.taskId).subscribe(
      () => {
        this.openSnackBar('Notes successfully submitted.');
      },
      () => {
        this.openSnackBar('Failed to submit notes.');
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

import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, ParamMap } from '@angular/router';
import CamundaForm from 'src/app/model/CamundaForm';
import CamundaFormItem from 'src/app/model/CamundaFormItem';
import { FormsService } from 'src/app/services/forms.service';
import { PlagiarismService } from 'src/app/services/plagiarism.service';

@Component({
  selector: 'app-editor-choices',
  templateUrl: './editor-choices.component.html',
  styleUrls: ['./editor-choices.component.scss']
})
export class EditorChoicesComponent implements OnInit {

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
          this.formService.getPlagiarismEditorChoicesForm(id).subscribe(
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

  selectEditors(fieldValues: any) {
    console.log(fieldValues);
    fieldValues.chosen_editors = fieldValues.chosen_editors.map((s: string) => s.substring(s.indexOf("(") + 1, s.lastIndexOf(")")));
    this.plagiarismService.selectEditorsToTakeNotes(fieldValues, this.taskId).subscribe(
      () => {
        this.openSnackBar('Editors successfully selected.');
      },
      () => {
        this.openSnackBar('Failed to select editors.');
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

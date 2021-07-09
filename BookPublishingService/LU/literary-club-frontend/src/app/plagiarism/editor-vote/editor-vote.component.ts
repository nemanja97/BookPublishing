import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, ParamMap } from '@angular/router';
import CamundaForm from 'src/app/model/CamundaForm';
import CamundaFormItem from 'src/app/model/CamundaFormItem';
import { FormsService } from 'src/app/services/forms.service';
import { PlagiarismService } from 'src/app/services/plagiarism.service';

@Component({
  selector: 'app-editor-vote',
  templateUrl: './editor-vote.component.html',
  styleUrls: ['./editor-vote.component.scss']
})
export class EditorVoteComponent implements OnInit {

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
          this.formService.getPlagiarismEditorVoteForm(id).subscribe(
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

  submitVote(fieldValues: any) {
    console.log(fieldValues);
    this.plagiarismService.castVote(fieldValues, this.taskId).subscribe(
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

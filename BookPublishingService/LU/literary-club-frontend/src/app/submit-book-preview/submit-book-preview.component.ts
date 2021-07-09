import {Component, OnInit} from '@angular/core';
import {BookService} from '../services/book.service';
import CamundaFormItem from '../model/CamundaFormItem';
import {StorageService} from '../services/storage.service';
import {ProcessService} from '../services/process.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ActivatedRoute, Router} from '@angular/router';
import {BookPublish} from '../model/BookPublish';
import {TaskService} from '../services/task.service';
import {FormsService} from '../services/forms.service';
import {FormFieldDTO} from '../model/FormFieldDTO';

@Component({
  selector: 'app-submit-book-preview',
  templateUrl: './submit-book-preview.component.html',
  styleUrls: ['./submit-book-preview.component.scss']
})
export class SubmitBookPreviewComponent implements OnInit {
  public formFields: CamundaFormItem[] = [];
  public loadingData = true;
  private processInstanceId = '';
  private taskId = '';

  constructor(private bookService: BookService,
              private taskService: TaskService,
              private storageService: StorageService,
              private processService: ProcessService,
              private snackBar: MatSnackBar,
              private route: ActivatedRoute,
              private router: Router,
              private formsService: FormsService) {
  }

  ngOnInit(): void {
    this.loadingData = true;
    this.startNewProcessAndGetForm();
  }

  publishBook(fieldValues: any): void {
    console.log(fieldValues);
    const fieldsArray = new Array<FormFieldDTO>();
    fieldsArray.push(new FormFieldDTO('title', fieldValues.title));
    fieldsArray.push(new FormFieldDTO('genres', fieldValues.genres));
    fieldsArray.push(new FormFieldDTO('synopsis', fieldValues.synopsis));
    console.log(fieldsArray);
    this.taskService.submitForm(fieldsArray, this.taskId).subscribe(
      () => {
        this.openSnackBar('Book publish request success');
        this.router.navigateByUrl('/writer_books');
      },
      () => {
        this.openSnackBar('Book publish request failure.');
      }
    );
  }

  openSnackBar(message: string): void {
    this.snackBar.open(message, 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }

  private startNewProcessAndGetForm(): void {
    this.processService.startProcess('book_publishing').subscribe(
      (res) => {
        this.processInstanceId = res;
        this.storageService.set('bookPublishingProcessId', res);
        this.taskService.getForm(this.processInstanceId, 'Activity_1ah74f0').subscribe(
          (response) => {
            this.processBookPublishingForm(response);
          },
          (err: any) => {
            this.loadingData = false;
          }
        );
      },
      (err: any) => {
        throw new Error(
          `Could not start a new book publishing process - ${err}`
        );
      }
    );
  }

  private processBookPublishingForm(res: any): void {
    this.taskId = res.taskId;
    this.formFields = res.formFields;
    this.formFields[1].properties.choices = this.formFields[1].properties.choices?.split(',');
    this.loadingData = false;
  }
}

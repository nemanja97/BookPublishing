import {Component, OnInit} from '@angular/core';
import CamundaFormItem from '../model/CamundaFormItem';
import {FormsService} from '../services/forms.service';
import {StorageService} from '../services/storage.service';
import {ProcessService} from '../services/process.service';
import {UserService} from '../services/user.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ActivatedRoute} from '@angular/router';
import CamundaForm from '../model/CamundaForm';
import {BookService} from '../services/book.service';

@Component({
  selector: 'app-writer-manuscript-upload',
  templateUrl: './writer-manuscript-upload.component.html',
  styleUrls: ['./writer-manuscript-upload.component.scss']
})
export class WriterManuscriptUploadComponent implements OnInit {

  bookId: string;
  public formFields: CamundaFormItem[] = [];
  public loadingData = true;
  processInstanceId = '';
  taskId = '';

  constructor(private formsService: FormsService,
              private storageService: StorageService,
              private processService: ProcessService,
              private userService: UserService,
              private snackBar: MatSnackBar,
              private route: ActivatedRoute,
              private bookService: BookService) {
  }

  ngOnInit(): void {
    /*this.bookId = String(this.route.snapshot.paramMap.get('id'));
    console.log(this.bookId);
    this.processService.getProcessForBook(this.bookId).subscribe(
      response => {
        this.formsService.getManuscriptUploadFormFields(response).subscribe(
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
    );
     */
  }

  submitWork(fieldValues: any) {
    /*console.log(fieldValues);
    this.bookService.writerSubmitsManuscript(fieldValues, this.taskId,this.bookId).subscribe(
      () => {
        this.snackBar.open('Work successfully submitted.');
      },
      () => {
        this.snackBar.open('Error submitting work.');
      }
    );*/
  }
}

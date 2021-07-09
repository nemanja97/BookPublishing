import {Component, OnInit} from '@angular/core';
import {Book} from '../../model/Book';
import {UserService} from '../services/user.service';
import {BookService} from '../services/book.service';
import {ActivatedRoute} from '@angular/router';
import {Role} from '../model/enum/Roles';
import CamundaFormItem from '../model/CamundaFormItem';
import {ProcessService} from '../services/process.service';
import {FormsService} from '../services/forms.service';
import CamundaForm from '../model/CamundaForm';
import {MatSnackBar} from '@angular/material/snack-bar';
import {RequestEditorChanges} from '../model/RequestEditorChanges';
import {DecideSuitableForPublishing} from '../model/DecideSuitableForPublishing';
import {LectorReview} from '../model/LectorReview';
import {DecideNeedsChanges} from '../model/DecideNeedsChanges';
import {BetaReaderReviewSubmission} from '../model/BetaReaderReviewSubmission';
import {AssignBetaReaders} from '../model/AssignBetaReaders';
import {DecideNeedsBetaReaders} from '../model/DecideNeedsBetaReaders';
import {DecideOnPublishing} from '../model/DecideOnPublishing';
import {BetaReaderReview} from '../model/BetaReaderReview';
import {EditorChanges} from '../model/EditorChanges';
import * as FileSaver from 'file-saver';
import {TaskService} from '../services/task.service';
import {FileService} from '../services/file.service';
import {FormFieldDTO} from '../model/FormFieldDTO';

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.scss']
})
export class BookDetailsComponent implements OnInit {

  book: Book;
  bookId: string;
  taskId: string;
  processId = '';
  public betaReaderReviews: Array<BetaReaderReview>;
  public lectorReviews: Array<LectorReview>;
  public editorReviews: Array<EditorChanges>;
  initialReviewTaskId: string;
  public initialReviewFormFields: CamundaFormItem[] = [];
  public loadingInitialReviewData = true;
  writerUploadsManuscriptTaskId: string;
  public writerUploadsManuscriptFormFields: CamundaFormItem[] = [];
  public loadingWriterUploadsManuscriptData = true;
  plagiarismTaskId: string;
  public plagiarismFormFields: CamundaFormItem[] = [];
  public loadingPlagiarismData = true;
  decideOnPublishingTaskId: string;
  public decideOnPublishingFormFields: CamundaFormItem[] = [];
  public loadingDecideOnPublishingData = true;
  decideOnNeedsBetaReadersTaskId: string;
  public decideOnNeedsBetaReadersFormFields: CamundaFormItem[] = [];
  public loadingDecideOnNeedsBetaReadersData = true;
  pickBetaReadersTaskId: string;
  public pickBetaReadersFormFields: CamundaFormItem[] = [];
  public loadingPickBetaReadersData = true;
  pickedBetaReadersFormRequested = false;
  betaReaderReviewTaskId: string;
  public betaReaderReviewFormFields: CamundaFormItem[] = [];
  public loadingBetaReaderReviewData = true;
  writerUploadAgainTaskId: string;
  public writerUploadAgainFormFields: CamundaFormItem[] = [];
  public loadingWriterUploadAgainData = true;
  decideNeedsChangesTaskId: string;
  public decideNeedsChangesFormFields: CamundaFormItem[] = [];
  public loadingdecideNeedsChangesData = true;
  lectorChangesTaskId: string;
  public lectorChangesFormFields: CamundaFormItem[] = [];
  public loadingLectorChangesData = true;
  lectorChangesRequested = false;
  editorDecidesSuitabilityTaskId: string;
  public editorDecidesSuitabilityFormFields: CamundaFormItem[] = [];
  public loadingEditorDecidesSuitabilityData = true;
  editorNeedsFinalChangesTaskId: string;
  public editorNeedsFinalChangesFormFields: CamundaFormItem[] = [];
  public loadingEditorNeedsFinalChangesData = true;
  writerUploadsFinalManuscriptTaskId: string;
  public writerUploadsFinalManuscriptFormFields: CamundaFormItem[] = [];
  public loadingWriterUploadsFinalManuscriptData = true;
  private requestedInitialReviewForm = false;
  private requestedWriterManuscriptForm = false;
  private requestPlagiarismForm = false;
  private decideOnPublishingRequested = false;
  private decideOnNeedsBetaReadersRequested = false;
  private betaReaderReviewRequested = false;
  private writerUploadAgainRequested = false;
  private decideNeedsChangesRequested = false;
  private editorDecidesSuitabilityRequested = false;
  private editorNeedsFinalChangesRequested = false;
  private writerUploadsFinalManuscriptRequested = false;

  constructor(private userService: UserService,
              private bookService: BookService,
              private route: ActivatedRoute,
              private processService: ProcessService,
              private formsService: FormsService,
              private snackBar: MatSnackBar,
              private fileService: FileService,
              private taskService: TaskService) {
  }

  ngOnInit(): void {
    this.bookId = String(this.route.snapshot.paramMap.get('id'));
    this.taskId = String(this.route.snapshot.paramMap.get('taskId'));
    this.getBookFromApi(this.bookId);
    if (this.userService.getCurrentUserRole() === Role.WRITER) {
      this.getBetaReaderReviews();
      this.getLectorReviews();
      this.getEditorReviews();

    }
    this.processService.getProcessForBook(this.bookId).subscribe(
      response => {
        this.processId = response;
        console.log(this.processId);
      }
    );
  }

  getBookFromApi(bookId: string): void {
    this.bookService.getBook(bookId).subscribe(
      response => {
        this.book = response;
      }
    );
  }

  canUploadManuscript(): boolean {
    const canUpload = this.userService.getCurrentUserRole() === Role.WRITER && this.book.status === 'WAITING_FULL_MANUSCRIPT';
    if (canUpload && this.processId !== '' && !this.requestedWriterManuscriptForm) {
      this.requestedWriterManuscriptForm = true;
      this.taskService.getForm(this.processId, 'Activity_0ij7m6h').subscribe(
        (res: CamundaForm) => {
          this.writerUploadsManuscriptTaskId = res.taskId;
          this.writerUploadsManuscriptFormFields = res.formFields;
          this.loadingWriterUploadsManuscriptData = false;
        },
        (err: any) => {
          this.loadingWriterUploadsManuscriptData = false;
        }
      );
    }
    return canUpload;
  }

  uploadManuscript(fieldValues: any): void {
    this.taskService.submitFiles(fieldValues, this.writerUploadsManuscriptTaskId).subscribe(
      () => {
        this.snackBar.open('Work successfully submitted.');
      },
      () => {
        this.snackBar.open('Error submitting work.');
      }
    );
  }

  canEditorInitialReview(): boolean {
    const canDisplay = this.userService.getCurrentUserRole() === Role.EDITOR && this.book.status === 'CREATED';
    if (canDisplay && this.processId !== '' && !this.requestedInitialReviewForm) {
      this.requestedInitialReviewForm = true;
      console.log('Here');
      this.taskService.getForm(this.processId, 'Activity_1m55d2v').subscribe(
        res => {
          this.initialReviewTaskId = res.taskId;
          this.initialReviewFormFields = res.formFields;
          this.initialReviewFormFields[0].properties.choices = this.initialReviewFormFields[0].properties.choices?.split(',');
          this.loadingInitialReviewData = false;
        }
      );

    }
    return canDisplay;
  }


  askForManuscript(fieldValues: any): void {
    const fieldsArray = new Array<FormFieldDTO>();
    fieldsArray.push(new FormFieldDTO('suitable', fieldValues.suitable));
    this.taskService.submitForm(fieldsArray, this.initialReviewTaskId).subscribe(
      response => {
        alert('Successfully asked for manuscript');
        this.getBookFromApi(this.bookId);
      }, error => {
        alert(error);
      }
    );
  }

  canEditorPlagiarismReview(): boolean {
    const canDisplay = this.userService.getCurrentUserRole() === Role.EDITOR && this.book.status === 'UPLOADED_FULL_MANUSCRIPT';
    if (canDisplay && this.processId !== '' && !this.requestPlagiarismForm) {
      this.requestPlagiarismForm = true;
      console.log('Here');
      this.taskService.getForm(this.processId, 'Activity_18oj5li').subscribe(
        res => {
          this.plagiarismTaskId = res.taskId;
          this.plagiarismFormFields = res.formFields;
          this.plagiarismFormFields[0].properties.choices = this.plagiarismFormFields[0].properties.choices?.split(',');
          this.loadingPlagiarismData = false;
        }
      );

    }
    return canDisplay;
  }

  decidePlagiarism(fieldValues: any): void {
    /*const plagiarism = new BookPlagiarism(fieldValues.plagiarism === 'YES');
    this.bookService.decideIfSubmissionIsPlagiarised(plagiarism, this.bookId, this.plagiarismTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );*/
    const fieldsArray = new Array<FormFieldDTO>();
    fieldsArray.push(new FormFieldDTO('plagiarism', fieldValues.plagiarism));
    this.taskService.submitForm(fieldsArray, this.plagiarismTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );
  }

  canEditorDecideOnPublishing(): boolean {
    const canDisplay = this.userService.getCurrentUserRole() === Role.EDITOR && this.book.status === 'NOT_PLAGIARISM';
    if (canDisplay && this.processId && !this.decideOnPublishingRequested) {
      this.decideOnPublishingRequested = true;
      this.taskService.getForm(this.processId, 'Activity_1kr3u6f').subscribe(
        res => {
          this.decideOnPublishingTaskId = res.taskId;
          this.decideOnPublishingFormFields = res.formFields;
          this.decideOnPublishingFormFields[0].properties.choices = this.decideOnPublishingFormFields[0].properties.choices?.split(',');
          this.loadingDecideOnPublishingData = false;
        }
      );
    }
    return canDisplay;
  }

  decideOnPublishing(fieldValues: any): void {
   /* const decideOnPublishing = new DecideOnPublishing(fieldValues.shouldBePublished === 'YES');
    this.bookService.decideOnPublishing(decideOnPublishing, this.bookId, this.decideOnPublishingTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );*/
    const fieldsArray = new Array<FormFieldDTO>();
    fieldsArray.push(new FormFieldDTO('shouldBePublished', fieldValues.shouldBePublished));
    this.taskService.submitForm(fieldsArray, this.decideOnPublishingTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );
  }

  canEditorDecideOnNeedsBetaReaders(): boolean {
    const canDisplay = this.userService.getCurrentUserRole() === Role.EDITOR && this.book.status === 'DECIDE_ON_BETAREADERS';
    if (canDisplay && this.processId !== '' && !this.decideOnNeedsBetaReadersRequested) {
      this.decideOnNeedsBetaReadersRequested = true;
      this.taskService.getForm(this.processId, 'Activity_0qyulzd').subscribe(
        res => {
          this.decideOnNeedsBetaReadersTaskId = res.taskId;
          this.decideOnNeedsBetaReadersFormFields = res.formFields;
          this.decideOnNeedsBetaReadersFormFields[0].properties.choices = this.decideOnNeedsBetaReadersFormFields[0].properties.choices?.split(',');
          this.loadingDecideOnNeedsBetaReadersData = false;
        }
      );
    }
    return canDisplay;
  }

  decideOnNeedsBetaReaders(fieldValues: any): void {
    /*const decideNeedsBetaReaders = new DecideNeedsBetaReaders(fieldValues.needsBetareaders === 'YES');
    this.bookService.decideIfNeedsBetaReaders(decideNeedsBetaReaders, this.bookId, this.decideOnNeedsBetaReadersTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );*/
    const fieldsArray = new Array<FormFieldDTO>();
    fieldsArray.push(new FormFieldDTO('needsBetareaders', fieldValues.needsBetareaders));
    this.taskService.submitForm(fieldsArray, this.decideOnNeedsBetaReadersTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );
  }


  canEditorPickBetaReaders(): boolean {
    const canDisplay = this.userService.getCurrentUserRole() === Role.EDITOR && this.book.status === 'DECIDED_ON_BETAREADERS';
    if (canDisplay && this.processId !== '' && !this.pickedBetaReadersFormRequested) {
      this.pickedBetaReadersFormRequested = true;
      this.taskService.getForm(this.processId, 'Activity_16j577i').subscribe(
        res => {
          this.pickBetaReadersTaskId = res.taskId;
          this.pickBetaReadersFormFields = res.formFields;
          this.pickBetaReadersFormFields[0].properties.choices = this.pickBetaReadersFormFields[0].properties.choices?.split(',');
          this.loadingPickBetaReadersData = false;
        }
      );
    }
    return canDisplay;
  }

  pickBetaReaders(fieldValues: any): void {
    /*const assignBetaReaders = new AssignBetaReaders(fieldValues.betareaders);
    this.bookService.pickBetaReaders(assignBetaReaders, this.bookId, this.pickBetaReadersTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );*/
    const fieldsArray = new Array<FormFieldDTO>();
    fieldsArray.push(new FormFieldDTO('betareaders', fieldValues.betareaders));
    this.taskService.submitForm(fieldsArray, this.pickBetaReadersTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );
  }

  canBetaReaderReview(): boolean {
    const canDisplay = this.userService.getCurrentUserRole() === Role.READER && this.book.status === 'SENT_TO_BETA_READERS';
    if (canDisplay && this.processId !== '' && !this.betaReaderReviewRequested) {
      this.betaReaderReviewRequested = true;
      this.taskService.getForm(this.processId, 'Activity_1bm7mqy').subscribe(
        res => {
          this.betaReaderReviewTaskId = res.taskId;
          this.betaReaderReviewFormFields = res.formFields;
          this.loadingBetaReaderReviewData = false;
        }
      );
    }
    return canDisplay;
  }

  betaReaderReview(fieldValues: any): void {
    /*const betaReaderReview = new BetaReaderReviewSubmission(fieldValues.betaReaderComment);
    this.bookService.betaReaderSubmitsReview(betaReaderReview, this.bookId, this.betaReaderReviewTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );*/
    const fieldsArray = new Array<FormFieldDTO>();
    fieldsArray.push(new FormFieldDTO('betaReaderComment', fieldValues.betaReaderComment));
    this.taskService.submitForm(fieldsArray, this.betaReaderReviewTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );
  }

  canUploadManuscriptAgain(): boolean {
    const canDisplay = this.userService.getCurrentUserRole() === Role.WRITER && (this.book.status === 'FINISHED_BETA_READING' || this.book.status === 'LECTOR_ASKS_FOR_CHANGES');
    if (canDisplay && this.processId && !this.writerUploadAgainRequested) {
      this.writerUploadAgainRequested = true;
      this.taskService.getForm(this.processId, 'Activity_1rhu8nx').subscribe(
        res => {
          this.writerUploadAgainTaskId = res.taskId;
          this.writerUploadAgainFormFields = res.formFields;
          this.loadingWriterUploadAgainData = false;
        }
      );
    }
    return canDisplay;
  }

  uploadManuscriptAgain(fieldValues: any): void {
    this.taskService.submitFiles(fieldValues, this.writerUploadAgainTaskId).subscribe(
      () => {
        this.snackBar.open('Work successfully submitted.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error submitting work.');
      }
    );
  }

  canEditorDecideIfNeedsChange(): boolean {
    const canDisplay = this.userService.getCurrentUserRole() === Role.EDITOR && this.book.status === 'RE_UPLOADED_MANUSCRIPT';
    if (canDisplay && this.processId !== '' && !this.decideNeedsChangesRequested) {
      this.decideNeedsChangesRequested = true;
      this.taskService.getForm(this.processId, 'Activity_021jdmt').subscribe(
        res => {
          this.decideNeedsChangesTaskId = res.taskId;
          this.decideNeedsChangesFormFields = res.formFields;
          this.decideNeedsChangesFormFields[0].properties.choices = this.decideNeedsChangesFormFields[0].properties.choices?.split(',');
          this.loadingdecideNeedsChangesData = false;
        }
      );
    }
    return canDisplay;
  }

  decideNeedsChanges(fieldValues: any): void {
    /*const decideNeedsChanges = new DecideNeedsChanges(fieldValues.needsChanges === 'YES');
    this.bookService.decideIfSubmissionNeedsChanges(decideNeedsChanges, this.bookId, this.decideNeedsChangesTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );*/
    const fieldsArray = new Array<FormFieldDTO>();
    fieldsArray.push(new FormFieldDTO('needsChanges', fieldValues.needsChanges));
    this.taskService.submitForm(fieldsArray, this.decideNeedsChangesTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );
  }

  canLectorReview(): boolean {
    const canDisplay = this.userService.getCurrentUserRole() === Role.LECTOR && this.book.status === 'SENT_TO_LECTOR';
    if (canDisplay && this.processId !== '' && !this.lectorChangesRequested) {
      this.lectorChangesRequested = true;
      this.taskService.getForm(this.processId, 'Activity_1tb5l9i').subscribe(
        res => {
          this.lectorChangesTaskId = res.taskId;
          this.lectorChangesFormFields = res.formFields;
          this.lectorChangesFormFields[1].properties.choices = this.lectorChangesFormFields[1].properties.choices?.split(',');
          this.loadingLectorChangesData = false;
        }
      );
    }
    return canDisplay;
  }

  lectorReview(fieldValues: any): void {
    /*const lectorReview = new LectorReview(fieldValues.lectorComment, fieldValues.needsCorrections === 'YES');
    this.bookService.lectorMarksErrors(lectorReview, this.bookId, this.lectorChangesTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );*/
    const fieldsArray = new Array<FormFieldDTO>();
    fieldsArray.push(new FormFieldDTO('lectorComment', fieldValues.lectorComment));
    fieldsArray.push(new FormFieldDTO('needsCorrections', fieldValues.needsCorrections));
    this.taskService.submitForm(fieldsArray, this.lectorChangesTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );
  }

  canEditorDecideSuitabilityToPublish(): boolean {
    const canDisplay = this.userService.getCurrentUserRole() === Role.EDITOR && this.book.status === 'LECTOR_APPROVES';
    if (canDisplay && this.processId !== '' && !this.editorDecidesSuitabilityRequested) {
      this.editorDecidesSuitabilityRequested = true;
      this.taskService.getForm(this.processId, 'Activity_03a4v0v').subscribe(
        res => {
          this.editorDecidesSuitabilityTaskId = res.taskId;
          this.editorDecidesSuitabilityFormFields = res.formFields;
          this.editorDecidesSuitabilityFormFields[0].properties.choices = this.editorDecidesSuitabilityFormFields[0].properties.choices?.split(',');
          this.loadingEditorDecidesSuitabilityData = false;
        }
      );
    }
    return canDisplay;
  }

  decideOnSuitability(fieldValues: any): void {
    /*const decideSuitableForPublishing = new DecideSuitableForPublishing(fieldValues.suitable === 'YES');
    this.bookService.decideIfSuitableForPublishing(decideSuitableForPublishing, this.bookId, this.editorDecidesSuitabilityTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );*/
    const fieldsArray = new Array<FormFieldDTO>();
    fieldsArray.push(new FormFieldDTO('suitable', fieldValues.suitable));
    this.taskService.submitForm(fieldsArray, this.editorDecidesSuitabilityTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );
  }

  canEditorAskForFinalChanges(): boolean {
    const canDisplay = this.userService.getCurrentUserRole() === Role.EDITOR && this.book.status === 'EDITOR_ASKS_FOR_CHANGES';
    if (canDisplay && this.processId !== '' && !this.editorNeedsFinalChangesRequested) {
      this.editorNeedsFinalChangesRequested = true;
      this.taskService.getForm(this.processId, 'Activity_0pq36uv').subscribe(
        res => {
          this.editorNeedsFinalChangesTaskId = res.taskId;
          this.editorNeedsFinalChangesFormFields = res.formFields;
          this.loadingEditorNeedsFinalChangesData = false;
        }
      );
    }
    return canDisplay;
  }

  askForFinalChanges(fieldValues: any): void {
    /*const requestEditorChanges = new RequestEditorChanges(fieldValues.changes);
    this.bookService.requestEditorChanges(requestEditorChanges, this.bookId, this.editorNeedsFinalChangesTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );*/
    const fieldsArray = new Array<FormFieldDTO>();
    fieldsArray.push(new FormFieldDTO('changes', fieldValues.changes));
    this.taskService.submitForm(fieldsArray, this.editorNeedsFinalChangesTaskId).subscribe(
      () => {
        this.snackBar.open('Success.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error.');
      }
    );
  }

  canWriterUploadsFinalManuscript(): boolean {
    const canDisplay = this.userService.getCurrentUserRole() === Role.WRITER && this.book.status === 'WAITING_FINAL_CHANGES';
    if (canDisplay && this.processId !== '' && !this.writerUploadsFinalManuscriptRequested) {
      this.writerUploadsFinalManuscriptRequested = true;
      this.taskService.getForm(this.processId, 'Activity_0f4l0ln').subscribe(
        res => {
          this.writerUploadsFinalManuscriptTaskId = res.taskId;
          this.writerUploadsFinalManuscriptFormFields = res.formFields;
          this.loadingWriterUploadsFinalManuscriptData = false;
        }
      );
    }
    return canDisplay;
  }

  uploadFinalManuscript(fieldValues: any): void {
    this.taskService.submitFiles(fieldValues, this.writerUploadsFinalManuscriptTaskId).subscribe(
      () => {
        this.snackBar.open('Work successfully submitted.');
        this.getBookFromApi(this.bookId);
      },
      () => {
        this.snackBar.open('Error submitting work.');
      }
    );
  }

  getBetaReaderReviews(): void {
    this.bookService.getBetaReaderReviews(this.bookId).subscribe(
      response => {
        this.betaReaderReviews = response;
      }
    );
  }

  getLectorReviews(): void {
    this.bookService.getLectorReviews(this.bookId).subscribe(
      response => {
        this.lectorReviews = response;
      }
    );
  }

  getEditorReviews(): void {
    this.bookService.getEditorReviews(this.bookId).subscribe(
      response => {
        this.editorReviews = response;
      }
    );
  }

  canDownload(): boolean {
    return this.book.status !== 'CREATED' && this.book.status !== 'WAITING_FULL_MANUSCRIPT';
  }

  download(): void {
    this.fileService
      .getBookAsPdf(this.bookId)
      .subscribe((data) => {
          FileSaver.saveAs(data, `book-${this.bookId}.zip`);
        },
        (err) => {
        console.log(err);
          this.snackBar.open('Unable to download files.');
        });
  }

  isWriter(): boolean {
    return this.userService.getCurrentUserRole() === Role.WRITER;
  }
}

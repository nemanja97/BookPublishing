import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatCardModule } from '@angular/material/card';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressBarModule } from '@angular/material/progress-bar';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CamundaDynamicFormComponent } from './forms/camunda-dynamic-form/camunda-dynamic-form.component';
import { CamundaTextInputFieldComponent } from './forms/camunda-text-input-field/camunda-text-input-field.component';
import { CamundaBooleanInputFieldComponent } from './forms/camunda-boolean-input-field/camunda-boolean-input-field.component';
import { CamundaMultiSelectInputFieldComponent } from './forms/camunda-multi-select-input-field/camunda-multi-select-input-field.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeaderComponent } from './shared/header/header.component';
import { ShowAuthedDirective } from './shared/directives/show-authed.directive';
import { LoginComponent } from './auth/login/login.component';
import { WriterRegistrationComponent } from './writer-registration/writer-registration.component';
import { ReaderRegistrationComponent } from './reader-registration/reader-registration.component';
import { EmailVerificationComponent } from './email-verification/email-verification.component';
import { WriterAssociationRegistrationComponent } from './writer-association-registration/writer-association-registration.component';
import { VoteOnWriterMembershipApplicationComponent } from './vote-on-writer-membership-application/vote-on-writer-membership-application.component';
import { HomepageComponent } from './homepage/homepage.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { CamundaFileUploadComponent } from './forms/camunda-file-upload/camunda-file-upload.component';
import { BetaReaderRegistrationComponent } from './beta-reader-registration/beta-reader-registration.component';
import { EditorTasksComponent } from './editor-tasks/editor-tasks.component';
import { CamundaSelectInputComponent } from './forms/camunda-select-input/camunda-select-input.component';
import { WriterHomeComponent } from './writer-home/writer-home.component';
import { AddBookComponent } from './add-book/add-book.component';
import { BookDetailsComponent } from './book-details/book-details.component';
import { BetaReaderHomeComponent } from './beta-reader-home/beta-reader-home.component';
import { LectorHomeComponent } from './lector-home/lector-home.component';
import {MatListModule} from '@angular/material/list';
import {MatTableModule} from '@angular/material/table';
import { BooksTableComponent } from './books-table/books-table.component';
import {MatTabsModule} from '@angular/material/tabs';
import { SubmitBookPreviewComponent } from './submit-book-preview/submit-book-preview.component';
import {TokenInterceptorService} from './services/token-interceptor.service';
import { EditorBookReviewComponent } from './editor-book-review/editor-book-review.component';
import { EditorBookTableComponent } from './editor-book-table/editor-book-table.component';
import { WriterManuscriptUploadComponent } from './writer-manuscript-upload/writer-manuscript-upload.component';
import { WriterBooksComponent } from './writer-books/writer-books.component';
import { ReaderBooksComponent } from './reader-books/reader-books.component';
import { LectorBooksComponent } from './lector-books/lector-books.component';
import { PlagiarismBlameComponent } from './plagiarism/plagiarism-blame/plagiarism-blame.component';
import { EditorChoicesComponent } from './plagiarism/editor-choices/editor-choices.component';
import { ReplacementEditorChoiceComponent } from './plagiarism/replacement-editor-choice/replacement-editor-choice.component';
import { EditorNotesComponent } from './plagiarism/editor-notes/editor-notes.component';
import { EditorVoteComponent } from './plagiarism/editor-vote/editor-vote.component';

@NgModule({
  declarations: [
    AppComponent,
    CamundaDynamicFormComponent,
    CamundaTextInputFieldComponent,
    CamundaBooleanInputFieldComponent,
    CamundaMultiSelectInputFieldComponent,
    WriterHomeComponent,
    AddBookComponent,
    BookDetailsComponent,
    BetaReaderHomeComponent,
    LectorHomeComponent,
    BooksTableComponent,
    HeaderComponent,
    ShowAuthedDirective,
    LoginComponent,
    WriterRegistrationComponent,
    ReaderRegistrationComponent,
    EmailVerificationComponent,
    WriterAssociationRegistrationComponent,
    VoteOnWriterMembershipApplicationComponent,
    HomepageComponent,
    PageNotFoundComponent,
    CamundaFileUploadComponent,
    BetaReaderRegistrationComponent,
    EditorTasksComponent,
    CamundaSelectInputComponent,
    SubmitBookPreviewComponent,
    EditorBookReviewComponent,
    EditorBookTableComponent,
    WriterManuscriptUploadComponent,
    WriterBooksComponent,
    ReaderBooksComponent,
    LectorBooksComponent,
    PlagiarismBlameComponent,
    EditorChoicesComponent,
    ReplacementEditorChoiceComponent,
    EditorNotesComponent,
    EditorVoteComponent,
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    MatProgressBarModule,
    MatSelectModule,
    MatFormFieldModule,
    MatInputModule,
    MatSlideToggleModule,
    MatCheckboxModule,
    MatProgressSpinnerModule,
    MatButtonModule,
    MatCardModule,
    MatListModule,
    MatToolbarModule,
    MatCardModule,
    MatGridListModule,
    MatSnackBarModule,
    MDBBootstrapModule.forRoot(),
    NgbModule,
    MatTableModule,
    MatTabsModule,
    NgbModule,
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptorService,
    multi: true
  }],
  bootstrap: [AppComponent],
})
export class AppModule {}

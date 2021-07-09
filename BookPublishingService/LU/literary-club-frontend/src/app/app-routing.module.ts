import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { WriterHomeComponent } from './writer-home/writer-home.component';
import { BookDetailsComponent } from './book-details/book-details.component';
import { LoginComponent } from './auth/login/login.component';
import { BetaReaderRegistrationComponent } from './beta-reader-registration/beta-reader-registration.component';
import { EmailVerificationComponent } from './email-verification/email-verification.component';
import { AuthGuard } from './guards/auth.guard';
import { HomepageComponent } from './homepage/homepage.component';
import { Role } from './model/enum/Roles';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { ReaderRegistrationComponent } from './reader-registration/reader-registration.component';
import { WriterAssociationRegistrationComponent } from './writer-association-registration/writer-association-registration.component';
import { WriterRegistrationComponent } from './writer-registration/writer-registration.component';
import { EditorTasksComponent } from './editor-tasks/editor-tasks.component';
import { VoteOnWriterMembershipApplicationComponent } from './vote-on-writer-membership-application/vote-on-writer-membership-application.component';
import { SubmitBookPreviewComponent } from './submit-book-preview/submit-book-preview.component';
import { EditorBookReviewComponent } from './editor-book-review/editor-book-review.component';
import { WriterManuscriptUploadComponent } from './writer-manuscript-upload/writer-manuscript-upload.component';
import { WriterBooksComponent } from './writer-books/writer-books.component';
import { LectorBooksComponent } from './lector-books/lector-books.component';
import { ReaderBooksComponent } from './reader-books/reader-books.component';

import { PlagiarismBlameComponent } from './plagiarism/plagiarism-blame/plagiarism-blame.component';
import { EditorChoicesComponent } from './plagiarism/editor-choices/editor-choices.component';
import { ReplacementEditorChoiceComponent } from './plagiarism/replacement-editor-choice/replacement-editor-choice.component';
import { EditorNotesComponent } from './plagiarism/editor-notes/editor-notes.component';
import { EditorVoteComponent } from './plagiarism/editor-vote/editor-vote.component';

const routes: Routes = [
  { path: 'book/:id/:taskId', component: BookDetailsComponent },
  { path: 'book/:id', component: BookDetailsComponent },
  {
    path: 'login',
    component: LoginComponent,
    pathMatch: 'full',
  },
  {
    path: 'signUp/reader',
    component: ReaderRegistrationComponent,
    pathMatch: 'full',
  },
  {
    path: 'signUp/reader/beta',
    component: BetaReaderRegistrationComponent,
    pathMatch: 'full',
  },
  {
    path: 'signUp/writer',
    component: WriterRegistrationComponent,
    pathMatch: 'full',
  },
  {
    path: 'membership',
    component: WriterAssociationRegistrationComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard],
    data: { roles: [Role.WRITER] },
  },
  {
    path: 'plagiarism',
    component: PlagiarismBlameComponent,
    pathMatch: 'full',
  },
  {
    path: 'editor/books',
    component: EditorBookReviewComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard],
    data: { roles: [Role.EDITOR, Role.HEAD_EDITOR] },
  },
  {
    path: 'plagiarism/editor-choices/:processId',
    component: EditorChoicesComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard],
    data: { roles: [Role.HEAD_EDITOR] },
  },
  {
    path: 'plagiarism/replacement-editor-choice/:processId',
    component: ReplacementEditorChoiceComponent,
    pathMatch: 'full',
    data: { roles: [Role.HEAD_EDITOR] },
  },
  {
    path: 'plagiarism/editor/notes/:processId',
    component: EditorNotesComponent,
    pathMatch: 'full',
    data: { roles: [Role.HEAD_EDITOR, Role.EDITOR] },
  },
  {
    path: 'plagiarism/editor/vote/:processId',
    component: EditorVoteComponent,
    pathMatch: 'full',
    data: { roles: [Role.HEAD_EDITOR, Role.EDITOR] },
  },
  {
    path: 'editor/tasks',
    component: EditorTasksComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard],
    data: { roles: [Role.HEAD_EDITOR, Role.EDITOR] },
  },
  {
    path: 'editor/vote/:processId',
    component: VoteOnWriterMembershipApplicationComponent,
    pathMatch: 'full',
    canActivate: [AuthGuard],
    data: { roles: [Role.HEAD_EDITOR, Role.EDITOR] },
  },
  {
    path: 'verifyAccount/:id',
    component: EmailVerificationComponent,
    pathMatch: 'full',
  },
  {
    path: 'home',
    component: HomepageComponent,
    pathMatch: 'full',
  },

  {
    path: 'submit_book_preview',
    component: SubmitBookPreviewComponent,
    pathMatch: 'full',
  },
  {
    path: 'writer_manuscript/:id',
    component: WriterManuscriptUploadComponent,
    pathMatch: 'full',
  },
  {
    path: 'writer_books',
    component: WriterBooksComponent,
    pathMatch: 'full',
  },
  {
    path: 'reader_books',
    component: ReaderBooksComponent,
    pathMatch: 'full',
  },
  {
    path: 'lector_books',
    component: LectorBooksComponent,
    pathMatch: 'full',
  },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}

import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {ApiService} from './api.service';
import {JwtService} from './jwt.service';
import {BookPublish} from '../model/BookPublish';
import {HttpClient} from '@angular/common/http';
import {Book} from '../../model/Book';
import {BookPreview} from '../../model/BookPreview';
import {EditorBook} from '../model/EditorBook';
import {BookSuitability} from '../model/BookSuitability';
import {BookPlagiarism} from '../model/BookPlagiarism';
import {AssignBetaReaders} from '../model/AssignBetaReaders';
import {BetaReaderReviewSubmission} from '../model/BetaReaderReviewSubmission';
import {DecideNeedsChanges} from '../model/DecideNeedsChanges';
import {LectorReview} from '../model/LectorReview';
import {DecideSuitableForPublishing} from '../model/DecideSuitableForPublishing';
import {RequestEditorChanges} from '../model/RequestEditorChanges';
import {DecideNeedsBetaReaders} from '../model/DecideNeedsBetaReaders';
import {DecideOnPublishing} from '../model/DecideOnPublishing';
import {EditorChanges} from '../model/EditorChanges';
import {BetaReaderReview} from '../model/BetaReaderReview';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  constructor(
    private apiService: ApiService,
    private jwtService: JwtService,
    private http: HttpClient
  ) {
  }

  sendBookRequest(book: BookPublish, taskId: string): Observable<any> {
    return this.http.post(`http://localhost:8080/api/publishing/sendPublishingRequest/${taskId}`, book);
  }

  getEditorBooks(): Observable<Array<BookPreview>> {
    return this.http.get<Array<BookPreview>>(`http://localhost:8080/api/publishing/editorBooks`);
  }

  getBook(bookId: string): Observable<Book> {
    return this.http.get<Book>(`http://localhost:8080/api/publishing/bookDetails/${bookId}`);
  }

  editorSubmitsInitialReview(bookSuitability: BookSuitability, bookId: string, taskId: string): Observable<EditorBook> {
    return this.http.post<EditorBook>(`http://localhost:8080/api/publishing/editorSubmitsInitialReview/${bookId}/${taskId}`, bookSuitability);
  }

  /*writerSubmitsManuscript(dto: any, taskId: string, bookId: string): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('manuscript', dto.manuscript[0]);
    return this.http.post<any>(`http://localhost:8080/api/publishing/upload_manuscript/${taskId}/${bookId}`, formData);
  }*/

  decideIfSubmissionIsPlagiarised(plagiarism: BookPlagiarism, bookId: string, taskId: string): Observable<EditorBook> {
    return this.http.post<EditorBook>(`http://localhost:8080/api/publishing/decide_if_plagiarism/${bookId}/${taskId}`, plagiarism);
  }

  decideOnPublishing(publishing: DecideOnPublishing, bookId: string, taskId: string): Observable<EditorBook> {
    return this.http.post<EditorBook>(`http://localhost:8080/api/publishing/decide_on_publishing/${bookId}/${taskId}`, publishing);
  }

  decideIfNeedsBetaReaders(needsBetaReaders: DecideNeedsBetaReaders, bookId: string, taskId: string): Observable<EditorBook> {
    return this.http.post<EditorBook>(`http://localhost:8080/api/publishing/decide_if_needs_betareaders/${bookId}/${taskId}`, needsBetaReaders);
  }

  pickBetaReaders(assignBetaReaders: AssignBetaReaders, bookId: string, taskId: string) {
    return this.http.post<EditorBook>(`http://localhost:8080/api/publishing/pick_betareaders/${bookId}/${taskId}`, assignBetaReaders);
  }

  betaReaderSubmitsReview(betaReaderReviewSubmission: BetaReaderReviewSubmission, bookId: string, taskId: string) {
    return this.http.post<EditorBook>(`http://localhost:8080/api/publishing/betareader_review/${bookId}/${taskId}`, betaReaderReviewSubmission);
  }

  /*uploadManuscriptAgain(dto: any, bookId: string, taskId: string) {
    const formData: FormData = new FormData();
    formData.append('manuscript', dto.manuscript[0]);
    return this.http.post<any>(`http://localhost:8080/api/publishing/upload_manuscript_again/${taskId}/${bookId}`, formData);
  }*/

  decideIfSubmissionNeedsChanges(decideNeedsChanges: DecideNeedsChanges, bookId: string, taskId: string) {
    return this.http.post(`http://localhost:8080/api/publishing/decide_on_changes/${bookId}/${taskId}`, decideNeedsChanges);
  }

  lectorMarksErrors(lectorReview: LectorReview, bookId: string, taskId: string) {
    return this.http.post(`http://localhost:8080/api/publishing/lector_marks_error/${bookId}/${taskId}`, lectorReview);
  }

  decideIfSuitableForPublishing(decideSuitableForPublishing: DecideSuitableForPublishing, bookId: string, taskId: string) {
    return this.http.post(`http://localhost:8080/api/publishing/decide_if_suitable_for_publishing/${bookId}/${taskId}`, decideSuitableForPublishing);
  }

  requestEditorChanges(requestEditorChanges: RequestEditorChanges, bookId: string, taskId: string) {
    return this.http.post(`http://localhost:8080/api/publishing/request_editor_changes/${bookId}/${taskId}`, requestEditorChanges);
  }

  /*uploadManuscriptFinal(dto: any, bookId: string, taskId: string) {
    const formData: FormData = new FormData();
    formData.append('manuscript', dto.manuscript[0]);
    return this.http.post<any>(`http://localhost:8080/api/publishing/upload_manuscript_final/${taskId}/${bookId}`, formData);
  }*/

  getBetaReaderReviews(bookId: string): Observable<Array<BetaReaderReview>> {
    return this.http.get<Array<BetaReaderReview>>(`http://localhost:8080/api/publishing/betaReaderReviews/${bookId}`);
  }

  getLectorReviews(bookId: string): Observable<Array<LectorReview>> {
    return this.http.get<Array<LectorReview>>(`http://localhost:8080/api/publishing/lectorReviews/${bookId}`);
  }

  getEditorReviews(bookId: string): Observable<Array<EditorChanges>> {
    return this.http.get<Array<EditorChanges>>(`http://localhost:8080/api/publishing/editorChanges/${bookId}`);
  }

  getWriterBooks(): Observable<Array<BookPreview>> {
    return this.http.get<Array<BookPreview>>(`http://localhost:8080/api/publishing/writerBooks`);
  }
  getLectorBooks(): Observable<Array<BookPreview>> {
    return this.http.get<Array<BookPreview>>(`http://localhost:8080/api/publishing/lectorBooks`);
  }
  getReaderBooks(): Observable<Array<BookPreview>> {
    return this.http.get<Array<BookPreview>>(`http://localhost:8080/api/publishing/readerBooks`);
  }
}

import {Component, OnInit} from '@angular/core';
import {BookPreview} from '../../model/BookPreview';
import {UserService} from '../services/user.service';
import {Role} from '../model/enum/Roles';
import {BookService} from '../services/book.service';
import {EditorBook} from '../model/EditorBook';

@Component({
  selector: 'app-editor-book-review',
  templateUrl: './editor-book-review.component.html',
  styleUrls: ['./editor-book-review.component.scss']
})
export class EditorBookReviewComponent implements OnInit {

  constructor(private userService: UserService, private bookService: BookService) {
  }

  books: Array<BookPreview>;
  selected: string;

  ngOnInit(): void {
    this.getBooksFromApi();
  }

  getBooksFromApi() {
    this.bookService.getEditorBooks().subscribe(
      response => {
        this.books = response;
      }
    );
  }

  isEditor(): boolean {
    return this.userService.getCurrentUserRole() === Role.EDITOR;
  }

}

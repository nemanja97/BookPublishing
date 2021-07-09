import {Component, OnInit} from '@angular/core';
import {BookService} from '../services/book.service';
import {BookPreview} from '../../model/BookPreview';

@Component({
  selector: 'app-writer-books',
  templateUrl: './writer-books.component.html',
  styleUrls: ['./writer-books.component.scss']
})
export class WriterBooksComponent implements OnInit {

  constructor(private bookService: BookService) {
  }

  public books: Array<BookPreview>;

  ngOnInit(): void {
    this.bookService.getWriterBooks().subscribe(
      response => {
        this.books = response;
      }
    );
  }

}

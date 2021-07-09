import { Component, OnInit } from '@angular/core';
import {BookService} from '../services/book.service';
import {BookPreview} from '../../model/BookPreview';

@Component({
  selector: 'app-reader-books',
  templateUrl: './reader-books.component.html',
  styleUrls: ['./reader-books.component.scss']
})
export class ReaderBooksComponent implements OnInit {

  constructor(private bookService: BookService) {
  }

  public books: Array<BookPreview>;

  ngOnInit(): void {
    this.bookService.getReaderBooks().subscribe(
      response => {
        this.books = response;
      }
    );
  }



}

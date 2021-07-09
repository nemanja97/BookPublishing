import { Component, OnInit } from '@angular/core';
import {BookService} from '../services/book.service';
import {BookPreview} from '../../model/BookPreview';

@Component({
  selector: 'app-lector-books',
  templateUrl: './lector-books.component.html',
  styleUrls: ['./lector-books.component.scss']
})
export class LectorBooksComponent implements OnInit {

  constructor(private bookService: BookService) {
  }

  public books: Array<BookPreview>;

  ngOnInit(): void {
    this.bookService.getLectorBooks().subscribe(
      response => {
        this.books = response;
      }
    );
  }

}

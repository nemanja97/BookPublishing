import {Component, OnInit} from '@angular/core';
import {BookPreview} from '../../model/BookPreview';

@Component({
  selector: 'app-writer-home',
  templateUrl: './writer-home.component.html',
  styleUrls: ['./writer-home.component.scss']
})
export class WriterHomeComponent implements OnInit {

  constructor() {
  }

  books: Array<BookPreview>;
  selected: string;
  role: string;

  ngOnInit(): void {
    this.getBooksFromApi();
    this.getRole();
  }

  getBooksFromApi() {

  }

  getRole() {
    this.role = 'writer';
  }

}

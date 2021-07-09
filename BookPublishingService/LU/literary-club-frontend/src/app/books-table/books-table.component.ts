import {Component, Input, OnInit} from '@angular/core';
import {BookPreview} from '../../model/BookPreview';
import {Router} from '@angular/router';

@Component({
  selector: 'app-books-table',
  templateUrl: './books-table.component.html',
  styleUrls: ['./books-table.component.scss']
})
export class BooksTableComponent implements OnInit {

  constructor(private router: Router) {
  }

  displayedColumns: string[] = ['authorName', 'bookName', 'status', 'details'];
  @Input() books: Array<BookPreview>;

  ngOnInit(): void {
  }

  details(id: number) {
    console.log("trigger");
    this.router.navigateByUrl('book/' + id);
  }

}

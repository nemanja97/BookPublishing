import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {BookPreview} from '../../model/BookPreview';
import {EditorBook} from '../model/EditorBook';

@Component({
  selector: 'app-editor-book-table',
  templateUrl: './editor-book-table.component.html',
  styleUrls: ['./editor-book-table.component.scss']
})
export class EditorBookTableComponent implements OnInit {

  constructor(private router: Router) {
  }

  displayedColumns: string[] = ['authorName', 'bookName', 'status', 'taskName', 'details'];
  @Input() books: Array<EditorBook>;

  ngOnInit(): void {
  }

  details(id: string, taskId: string) {
    console.log('trigger');
    this.router.navigateByUrl('book/' + id + '/' + taskId);
  }
}

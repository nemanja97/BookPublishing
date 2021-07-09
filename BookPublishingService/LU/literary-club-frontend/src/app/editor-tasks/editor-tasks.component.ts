import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Task } from '../model/Task';
import { FileService } from '../services/file.service';
import { TaskService } from '../services/task.service';
import * as FileSaver from 'file-saver';
@Component({
  selector: 'app-editor-tasks',
  templateUrl: './editor-tasks.component.html',
  styleUrls: ['./editor-tasks.component.scss'],
})
export class EditorTasksComponent implements OnInit {
  public tasks: Task[] = [];

  constructor(
    private taskService: TaskService,
    private fileService: FileService,
    private snackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.taskService.getTasksForCurrentUser().subscribe(
      (res: any) => {
        console.log(res);
        this.tasks = res;
      },
      (err: any) => {
        console.error(err);
      }
    );
  }

  goToTask(task: Task) {
    console.log(task);
    // TODO Implement properly
    if (task.name == 'Choose editors for evaluation') {
      console.log('Choose editors for evaluation');
      this.router.navigate([`plagiarism/editor-choices/${task.processId}`]);
    } else if (task.name == 'Make notes') {
      this.router.navigate([`plagiarism/editor/notes/${task.processId}`]);
    } else if (task.name == 'Vote if book is plagiarised') {
      this.router.navigate([`plagiarism/editor/vote/${task.processId}`]);
    } else {
      this.router.navigate([`editor/vote/${task.processId}`]);
    }
  }

  download(task: Task) {
    if (
      task.name == 'Make notes' ||
      task.name == 'Vote if book is plagiarised'
    ) {
      this.fileService.getPossiblePlagiarizedWorks(task.processId).subscribe(
        (data) => {
          FileSaver.saveAs(data, `plagiarizedReport-${task.processId}.zip`);
        },
        (err) => {
          this.openSnackBar('Unable to download files.');
        }
      );
    } else {
      this.fileService
        .getWriterAssociationSubmittedWorks(task.processId)
        .subscribe(
          (data) => {
            FileSaver.saveAs(data, `writerSubmission-${task.processId}.zip`);
          },
          (err) => {
            this.openSnackBar('Unable to download files.');
          }
        );
    }
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, 'Close', {
      horizontalPosition: 'center',
      verticalPosition: 'top',
    });
  }
}

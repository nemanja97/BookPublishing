import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-add-bank-info',
  templateUrl: './add-bank-info.component.html',
  styleUrls: ['./add-bank-info.component.scss']
})
export class AddBankInfoComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
  }

}

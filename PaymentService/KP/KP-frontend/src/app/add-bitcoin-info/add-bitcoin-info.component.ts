import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';

@Component({
  selector: 'app-add-bitcoin-info',
  templateUrl: './add-bitcoin-info.component.html',
  styleUrls: ['./add-bitcoin-info.component.scss']
})
export class AddBitcoinInfoComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit() {
  }

}

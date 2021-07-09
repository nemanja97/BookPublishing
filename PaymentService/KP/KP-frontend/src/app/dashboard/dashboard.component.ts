import {Component, OnInit} from '@angular/core';
import {AddPaypalInfoComponent} from '../add-paypal-info/add-paypal-info.component';
import {AddBitcoinInfoComponent} from '../add-bitcoin-info/add-bitcoin-info.component';
import {MatDialog} from '@angular/material/dialog';
import {PaymentService} from '../service/payment.service';
import {PayPalPayment} from '../model/PayPalPayment';
import {BitcoinPayment} from '../model/BitcoinPayment';
import {Router} from '@angular/router';
import {AddBankInfoComponent} from '../add-bank-info/add-bank-info.component';
import {BankInfo} from '../model/BankInfo';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  storeOptions = Array<string>();
  storeId: string;

  constructor(public dialog: MatDialog,
              private paymentService: PaymentService,
              private router: Router,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.storeOptions = [];
    this.getStoreOptions();
    this.getStoreId();
  }

  openPaypalDialog() {
    if (this.storeOptions.find(value => value === 'PAYPAL')) {
      this.snackBar.open('You have already added paypal info');
      return;
    }
    const dialogRef = this.dialog.open(AddPaypalInfoComponent, {
      width: '250px',
      data: {paypalInfo: {id: '', secret: ''}}
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const paypalInfo = new PayPalPayment(result.id, result.secret);
        this.paymentService.addPaypalPaymentOption(paypalInfo).subscribe(
          response => {
            this.snackBar.open('Paypal info added');
            this.storeOptions.push('PAYPAL');
          }, error => {
            this.snackBar.open('Error adding Paypal info');
          }
        );
      }
    });
  }

  openBitcoinDilog() {
    if (this.storeOptions.find(value => value === 'BITCOIN')) {
      this.snackBar.open('You have already added bitcoin info');
      return;
    }
    const dialogRef = this.dialog.open(AddBitcoinInfoComponent, {
      width: '250px',
      data: {api: ''}
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const bitcoinInfo = new BitcoinPayment(result);
        this.paymentService.addBitcoinPaymentOption(bitcoinInfo).subscribe(
          response => {
            this.snackBar.open('Bitcoin info added');
            this.storeOptions.push('BITCOIN');
          }, error => {
            this.snackBar.open('Error adding Bitcoin info');
          }
        );
      }
    });
  }

  openBankDialog() {
    if (this.storeOptions.find(value => value === 'CARD')) {
      this.snackBar.open('You have already added bank info');
      return;
    }
    const dialogRef = this.dialog.open(AddBankInfoComponent, {
      width: '250px',
      data: {bankInfo: {bankName: '', merchantId: '', merchantPassword: ''}}
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const bankInfo = new BankInfo(result.bankName, result.merchantId, result.merchantPassword);
        this.paymentService.addCardPaymentOption(bankInfo).subscribe(
          response => {
            this.snackBar.open('Bank info added');
            this.storeOptions.push('CARD');
          }, error => {
            this.snackBar.open('Error adding bank info');
          }
        );
      }
    });
  }

  getStoreOptions() {
    this.paymentService.getStorePaymentOptions().subscribe(
      response => {
        console.log(response);
        this.storeOptions = response;
      }
    );
  }

  getStoreId() {
    this.paymentService.getStoreId().subscribe(
      response => {
        this.storeId = response;
      }, error => {
        console.log(error);
        this.storeId = error.error.text;
      }
    );
  }

  hasOption(option: string): boolean {
    console.log(this.storeOptions.find(value => value === option));
    if (this.storeOptions.find(value => value === option)) {
      return true;
    }
    return false;
  }
}

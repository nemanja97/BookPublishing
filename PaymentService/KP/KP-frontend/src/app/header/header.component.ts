import {Component, OnInit} from '@angular/core';
import {PaymentService} from '../service/payment.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  storeOptions = Array<string>();

  constructor(private paymentService: PaymentService) {
  }

  ngOnInit(): void {
    this.getStoreOptions();
  }

  loggedIn(): boolean {
    if (localStorage.getItem('token')) {
      return true;
    }
    return false;
  }

  logout() {
    localStorage.removeItem('token');
  }

  getStoreOptions() {
    this.paymentService.getStorePaymentOptions().subscribe(
      response => {
        this.storeOptions = response;
      }
    );
  }

  hasOption(option: string): boolean {
    if (this.storeOptions.find(value => value === option)) {
      return true;
    }
    return false;
  }

}

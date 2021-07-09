import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {OrderPaypal} from '../model/OrderPaypal';
import {Observable} from 'rxjs';
import {PaypalResponse} from '../model/paypalResponse';
import {PayPalPayment} from '../model/PayPalPayment';
import {BitcoinPayment} from '../model/BitcoinPayment';
import {environment} from 'src/environments/environment';
import {BankInfo} from '../model/BankInfo';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {

  private path = `${environment.kpApi}/`;

  constructor(private http: HttpClient) {
  }

  payWithPaypal(order: OrderPaypal): Observable<PaypalResponse> {
    return this.http.post<PaypalResponse>(this.path + 'kp/paypal', order);
  }

  addCardPaymentOption(cardPayment: BankInfo) {
    return this.http.post(this.path + 'payment/card', cardPayment);
  }

  addPaypalPaymentOption(paypal: PayPalPayment) {
    return this.http.post(this.path + 'payment/paypal', paypal);
  }

  addBitcoinPaymentOption(bitcoin: BitcoinPayment) {
    return this.http.post(this.path + 'payment/bitcoin', bitcoin);
  }

  deleteCardPaymentOption() {
    return this.http.delete(this.path + 'payment/card');
  }

  deletePaypalPaymentOption() {
    return this.http.delete(this.path + 'payment/paypal');
  }

  deleteBitcoinPaymentOption() {
    return this.http.delete(this.path + 'payment/bitcoin');
  }

  getStorePaymentOptions(): Observable<Array<string>> {
    return this.http.get<Array<string>>(this.path + 'payment/options');
  }

  getStoreId(): Observable<string> {
    return this.http.get<string>(this.path + 'payment/storeId');
  }
}


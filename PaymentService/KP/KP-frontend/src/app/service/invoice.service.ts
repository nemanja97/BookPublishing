import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "src/environments/environment";
import Invoice from "../model/Invoice";
import { ListOfSubscriptionPlans } from "../model/ListOfSubscriptionPlans";
import { PaypalSubscriptionPlan } from "../model/PaypalSubscriptionPlan";
import {PaypalSubscriptionReceivedPlan} from '../model/PaypalSubscriptionReceivedPlan';

@Injectable({
  providedIn: "root",
})
export class InvoiceService {
  constructor(private http: HttpClient) {}

  getInvoice(id: String) {
    return this.http.get<Invoice>(`${environment.kpApi}/invoice/${id}/`);
  }

  payViaBank(id: String) {
    return this.http.post(
      `${environment.kpApi}/invoice/payment/bank/${id}`,
      null
    );
  }

  payViaPayPal(id: String) {
    return this.http.post(
      `${environment.kpApi}/invoice/payment/paypal/${id}`,
      null
    );
  }

  payViaBitcoin(id: String) {
    return this.http.post(
      `${environment.kpApi}/invoice/payment/bitcoin/${id}`,
      null
    );
  }

  createPlan(paypalSubscriptionPlan: PaypalSubscriptionPlan) {
    return this.http.post(
      `${environment.kpApi}/invoice/createPlan`,
      paypalSubscriptionPlan
    );
  }

  getPlans(storeId: string): Observable<ListOfSubscriptionPlans> {
    return this.http.get<ListOfSubscriptionPlans>(
      `${environment.kpApi}/invoice/plans/${storeId}`
    );
  }
  getPlansForUser(): Observable<ListOfSubscriptionPlans> {
    return this.http.get<ListOfSubscriptionPlans>(
      `${environment.kpApi}/invoice/plans`
    );
  }

  createAgreement(invoiceId: string): Observable<string> {
    return this.http.get<string>(
      `${environment.kpApi}/invoice/createAgreement/${invoiceId}`
    );
  }

  getPlanByInvoice(invoiceId: string): Observable<PaypalSubscriptionReceivedPlan> {
    return this.http.get<PaypalSubscriptionReceivedPlan>(
      `${environment.kpApi}/invoice/plan/${invoiceId}`
    );
  }
}

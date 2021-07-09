import {Component, OnInit} from '@angular/core';
import {PaypalSubscriptionPlan} from '../model/PaypalSubscriptionPlan';
import {ActivatedRoute} from '@angular/router';
import {InvoiceService} from '../service/invoice.service';

@Component({
  selector: 'app-subscription-invoice',
  templateUrl: './subscription-invoice.component.html',
  styleUrls: ['./subscription-invoice.component.scss']
})
export class SubscriptionInvoiceComponent implements OnInit {

  plan: PaypalSubscriptionPlan;
  invoiceId: string;

  constructor(private route: ActivatedRoute, private invoiceService: InvoiceService) {
  }

  ngOnInit(): void {
    this.invoiceId = this.route.snapshot.paramMap.get('invoiceId');
    this.getPlan();
  }

  getPlan() {
    this.invoiceService.getPlanByInvoice(this.invoiceId).subscribe(
      response => {
        this.plan = response;
      }, error => {
        console.log(error);
      }
    );
  }

  selectPlan() {
    this.invoiceService.createAgreement(this.invoiceId).subscribe(
      response => {
        console.log(response);
        document.location.href = response;
      },
      error => {
        if (error.status === 200) {
          document.location.href = error.error.text;
        }
      }
    );
  }

}

import {Component, OnInit} from '@angular/core';
import {ListOfSubscriptionPlans} from '../model/ListOfSubscriptionPlans';
import {ActivatedRoute} from '@angular/router';
import {InvoiceService} from '../service/invoice.service';

@Component({
  selector: 'app-paypal-subscribe',
  templateUrl: './paypal-subscribe.component.html',
  styleUrls: ['./paypal-subscribe.component.scss']
})
export class PaypalSubscribeComponent implements OnInit {

  constructor(private route: ActivatedRoute, private invoiceService: InvoiceService) {
  }

  plans: ListOfSubscriptionPlans;
  storeId: string;

  ngOnInit(): void {
    this.storeId = this.route.snapshot.paramMap.get('storeId');
    this.invoiceService.getPlans(this.storeId).subscribe(
      response => {
        this.plans = response;
      }
    );
  }

  goPlan(planId: number) {
    console.log(planId);
    /*this.invoiceService.createAgreement(planId).subscribe(
      response => {
        console.log(response);
        document.location.href = response;
      },
      error=>{
        if(error.status === 200){
          document.location.href = error.error.text;
        }
      }
    );*/
  }

}

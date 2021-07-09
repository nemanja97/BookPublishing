import {Component, OnInit} from '@angular/core';
import {InvoiceService} from '../service/invoice.service';
import {ListOfSubscriptionPlans} from '../model/ListOfSubscriptionPlans';

@Component({
  selector: 'app-view-plans',
  templateUrl: './view-plans.component.html',
  styleUrls: ['./view-plans.component.scss']
})
export class ViewPlansComponent implements OnInit {

  plans: ListOfSubscriptionPlans;

  constructor(private invoiceService: InvoiceService) {
  }

  ngOnInit(): void {
    this.invoiceService.getPlansForUser().subscribe(
      response => {
        console.log(response);
        this.plans = response;
      }, error => {
        console.log(error);
      }
    );
  }

}

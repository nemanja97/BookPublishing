import {Component, OnInit} from '@angular/core';
import {PaymentService} from '../service/payment.service';
import {error} from 'util';
import {OrderPaypal} from '../model/OrderPaypal';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-pay',
  templateUrl: './pay.component.html',
  styleUrls: ['./pay.component.scss']
})
export class PayComponent implements OnInit {

  private id: number;

  constructor(private paymentService: PaymentService, private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
  }

  paypal() {
    const order = new OrderPaypal();
    order.orderId = this.id;
    this.paymentService.payWithPaypal(order).subscribe(
      response => {
        console.log(response);
        document.location.href = response.url;
      }, e => {
        console.log(e);
      }
    );
  }

}

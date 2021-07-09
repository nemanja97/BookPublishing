import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {InvoiceService} from '../service/invoice.service';
import {PaypalSubscriptionPlan} from '../model/PaypalSubscriptionPlan';

@Component({
  selector: 'app-create-subscription-plan',
  templateUrl: './create-subscription-plan.component.html',
  styleUrls: ['./create-subscription-plan.component.scss']
})
export class CreateSubscriptionPlanComponent implements OnInit {

  planForm: FormGroup;

  constructor(private formBuilder: FormBuilder, private router: Router, private invoiceService: InvoiceService) {
  }

  get name() {
    return this.planForm.controls.name.value as string;
  }

  get description() {
    return this.planForm.controls.description.value as string;
  }

  get frequency() {
    return this.planForm.controls.frequency.value as string;
  }

  get freqInterval() {
    return this.planForm.controls.freqInterval.value as string;
  }

  get cycles() {
    return this.planForm.controls.cycles.value as string;
  }

  get amount() {
    return this.planForm.controls.amount.value as string;
  }

  get currency() {
    return this.planForm.controls.currency.value as string;
  }

  get amountStart() {
    return this.planForm.controls.amountStart.value as string;
  }

  ngOnInit(): void {
    this.planForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      description: ['', []],
      frequency: ['', []],
      freqInterval: ['', []],
      cycles: ['', []],
      amount: ['', []],
      currency: ['', []],
      amountStart: ['', []],
    });
  }

  onPlanSubmit() {
    const payPalSubscriptionPlan = new PaypalSubscriptionPlan(0, this.name, this.description, this.frequency,
      this.freqInterval, this.cycles, this.amount, this.currency, this.amountStart);
    console.log(payPalSubscriptionPlan);
    this.invoiceService.createPlan(payPalSubscriptionPlan).subscribe(
      response => {
        alert('Plan created');
        this.router.navigateByUrl('dashboard');
      },
      error => {
        if (error.status === 200) {
          alert('Plan created');
          this.router.navigateByUrl('dashboard');
        }
      }
    );
  }
}

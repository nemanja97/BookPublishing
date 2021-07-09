export class PaypalSubscriptionReceivedPlan {
  constructor(public id: number,
              public  name: string,
              public  description: string,
              public  frequency: string,
              public  freqInterval: string,
              public  cycles: string,
              public  amount: string,
              public  currency: string,
              public  amountStart: string,) {
  }
}

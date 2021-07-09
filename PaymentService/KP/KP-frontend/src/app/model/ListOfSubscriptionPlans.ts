import {PaypalSubscriptionPlan} from './PaypalSubscriptionPlan';
import {PaypalSubscriptionReceivedPlan} from './PaypalSubscriptionReceivedPlan';

export class ListOfSubscriptionPlans {
  constructor() {
  }
  public listOfPlans: Array<PaypalSubscriptionReceivedPlan>;
}

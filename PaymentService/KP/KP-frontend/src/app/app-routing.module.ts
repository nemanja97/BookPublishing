import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {PayComponent} from './pay/pay.component';
import {SuccessPageComponent} from './success-page/success-page.component';
import {ErrorPageComponent} from './error-page/error-page.component';
import {HomepageComponent} from './homepage/homepage.component';
import {LoginComponent} from './login/login.component';
import {RegistrationComponent} from './registration/registration.component';
import {DashboardComponent} from './dashboard/dashboard.component';
import { InvoicePageComponent } from './invoice-page/invoice-page.component';
import {CreateStoreComponent} from './create-store/create-store.component';
import {CreateSubscriptionPlanComponent} from './create-subscription-plan/create-subscription-plan.component';
import {PaypalSubscribeComponent} from './paypal-subscribe/paypal-subscribe.component';
import {ViewPlansComponent} from './view-plans/view-plans.component';
import {SubscriptionInvoiceComponent} from './subscription-invoice/subscription-invoice.component';


const routes: Routes = [
  {path: 'order/:id', component: PayComponent},
  {path: 'invoice/:id', component: InvoicePageComponent},
  {path: 'invoicePlan/:invoiceId', component: SubscriptionInvoiceComponent},
  {path: 'success', component: SuccessPageComponent},
  {path: 'failed', component: ErrorPageComponent},
  {path: '', component: HomepageComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegistrationComponent},
  {path: 'dashboard', component: DashboardComponent},
  {path: 'createStore', component: CreateStoreComponent},
  {path: 'createPlan', component: CreateSubscriptionPlanComponent},
  {path: 'plans', component: ViewPlansComponent},
  {path: 'subscribe/:storeId', component: PaypalSubscribeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}

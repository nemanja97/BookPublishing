import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {PayComponent} from './pay/pay.component';
import {HTTP_INTERCEPTORS, HttpClient, HttpClientModule} from '@angular/common/http';
import { SuccessPageComponent } from './success-page/success-page.component';
import { ErrorPageComponent } from './error-page/error-page.component';
import { HomepageComponent } from './homepage/homepage.component';
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MaterialModule} from './material/MaterialModule';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { AddBitcoinInfoComponent } from './add-bitcoin-info/add-bitcoin-info.component';
import { AddPaypalInfoComponent } from './add-paypal-info/add-paypal-info.component';
import { InvoicePageComponent } from './invoice-page/invoice-page.component';
import {TokenInterceptorService} from './service/token-interceptor.service';
import { CreateStoreComponent } from './create-store/create-store.component';
import { CreateSubscriptionPlanComponent } from './create-subscription-plan/create-subscription-plan.component';
import { PaypalSubscribeComponent } from './paypal-subscribe/paypal-subscribe.component';
import { HeaderComponent } from './header/header.component';
import { AddBankInfoComponent } from './add-bank-info/add-bank-info.component';
import { ViewPlansComponent } from './view-plans/view-plans.component';
import { SubscriptionInvoiceComponent } from './subscription-invoice/subscription-invoice.component';

@NgModule({
  declarations: [
    AppComponent,
    PayComponent,
    SuccessPageComponent,
    ErrorPageComponent,
    HomepageComponent,
    LoginComponent,
    RegistrationComponent,
    DashboardComponent,
    AddBitcoinInfoComponent,
    AddPaypalInfoComponent,
    InvoicePageComponent,
    CreateStoreComponent,
    CreateSubscriptionPlanComponent,
    PaypalSubscribeComponent,
    HeaderComponent,
    AddBankInfoComponent,
    ViewPlansComponent,
    SubscriptionInvoiceComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule,
    ReactiveFormsModule,
    FormsModule,
    MDBBootstrapModule.forRoot()
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptorService,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}

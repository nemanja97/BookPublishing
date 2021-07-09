import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaypalSubscribeComponent } from './paypal-subscribe.component';

describe('PaypalSubscribeComponent', () => {
  let component: PaypalSubscribeComponent;
  let fixture: ComponentFixture<PaypalSubscribeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaypalSubscribeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaypalSubscribeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

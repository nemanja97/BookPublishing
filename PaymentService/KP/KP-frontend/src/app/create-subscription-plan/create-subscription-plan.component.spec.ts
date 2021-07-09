import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSubscriptionPlanComponent } from './create-subscription-plan.component';

describe('CreateSubscriptionPlanComponent', () => {
  let component: CreateSubscriptionPlanComponent;
  let fixture: ComponentFixture<CreateSubscriptionPlanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateSubscriptionPlanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateSubscriptionPlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPaypalInfoComponent } from './add-paypal-info.component';

describe('AddPaypalInfoComponent', () => {
  let component: AddPaypalInfoComponent;
  let fixture: ComponentFixture<AddPaypalInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddPaypalInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddPaypalInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BetaReaderRegistrationComponent } from './beta-reader-registration.component';

describe('BetaReaderRegistrationComponent', () => {
  let component: BetaReaderRegistrationComponent;
  let fixture: ComponentFixture<BetaReaderRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BetaReaderRegistrationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BetaReaderRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

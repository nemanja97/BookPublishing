import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReaderRegistrationComponent } from './reader-registration.component';

describe('ReaderRegistrationComponent', () => {
  let component: ReaderRegistrationComponent;
  let fixture: ComponentFixture<ReaderRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReaderRegistrationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReaderRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

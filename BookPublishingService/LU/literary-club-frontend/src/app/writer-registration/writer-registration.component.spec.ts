import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WriterRegistrationComponent } from './writer-registration.component';

describe('WriterRegistrationComponent', () => {
  let component: WriterRegistrationComponent;
  let fixture: ComponentFixture<WriterRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WriterRegistrationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WriterRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

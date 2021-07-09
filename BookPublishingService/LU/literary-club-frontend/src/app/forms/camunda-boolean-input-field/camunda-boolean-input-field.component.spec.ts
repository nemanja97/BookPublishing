import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CamundaBooleanInputFieldComponent } from './camunda-boolean-input-field.component';

describe('CamundaBooleanInputFieldComponent', () => {
  let component: CamundaBooleanInputFieldComponent;
  let fixture: ComponentFixture<CamundaBooleanInputFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CamundaBooleanInputFieldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CamundaBooleanInputFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

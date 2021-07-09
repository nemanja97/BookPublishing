import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CamundaTextInputFieldComponent } from './camunda-text-input-field.component';

describe('CamundaTextInputFieldComponent', () => {
  let component: CamundaTextInputFieldComponent;
  let fixture: ComponentFixture<CamundaTextInputFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CamundaTextInputFieldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CamundaTextInputFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

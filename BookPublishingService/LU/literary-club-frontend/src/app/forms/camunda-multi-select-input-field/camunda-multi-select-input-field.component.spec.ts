import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CamundaMultiSelectInputFieldComponent } from './camunda-multi-select-input-field.component';

describe('CamundaMultiSelectInputFieldComponent', () => {
  let component: CamundaMultiSelectInputFieldComponent;
  let fixture: ComponentFixture<CamundaMultiSelectInputFieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CamundaMultiSelectInputFieldComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CamundaMultiSelectInputFieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

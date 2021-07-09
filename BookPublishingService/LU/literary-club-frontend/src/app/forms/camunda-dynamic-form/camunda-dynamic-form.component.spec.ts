import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CamundaDynamicFormComponent } from './camunda-dynamic-form.component';

describe('CamundaDynamicFormComponent', () => {
  let component: CamundaDynamicFormComponent;
  let fixture: ComponentFixture<CamundaDynamicFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CamundaDynamicFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CamundaDynamicFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

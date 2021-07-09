import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CamundaSelectInputComponent } from './camunda-select-input.component';

describe('CamundaSelectInputComponent', () => {
  let component: CamundaSelectInputComponent;
  let fixture: ComponentFixture<CamundaSelectInputComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CamundaSelectInputComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CamundaSelectInputComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

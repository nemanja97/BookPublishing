import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CamundaFileUploadComponent } from './camunda-file-upload.component';

describe('CamundaFileUploadComponent', () => {
  let component: CamundaFileUploadComponent;
  let fixture: ComponentFixture<CamundaFileUploadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CamundaFileUploadComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CamundaFileUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

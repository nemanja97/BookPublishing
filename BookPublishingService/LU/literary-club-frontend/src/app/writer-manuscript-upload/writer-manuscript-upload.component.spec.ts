import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WriterManuscriptUploadComponent } from './writer-manuscript-upload.component';

describe('WriterManuscriptUploadComponent', () => {
  let component: WriterManuscriptUploadComponent;
  let fixture: ComponentFixture<WriterManuscriptUploadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WriterManuscriptUploadComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WriterManuscriptUploadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

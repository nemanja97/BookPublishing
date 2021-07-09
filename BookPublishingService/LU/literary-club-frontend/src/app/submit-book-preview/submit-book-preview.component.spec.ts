import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SubmitBookPreviewComponent } from './submit-book-preview.component';

describe('SubmitBookPreviewComponent', () => {
  let component: SubmitBookPreviewComponent;
  let fixture: ComponentFixture<SubmitBookPreviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SubmitBookPreviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitBookPreviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

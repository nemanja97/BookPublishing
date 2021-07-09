import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorBookReviewComponent } from './editor-book-review.component';

describe('EditorBookReviewComponent', () => {
  let component: EditorBookReviewComponent;
  let fixture: ComponentFixture<EditorBookReviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditorBookReviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorBookReviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorBookTableComponent } from './editor-book-table.component';

describe('EditorBookTableComponent', () => {
  let component: EditorBookTableComponent;
  let fixture: ComponentFixture<EditorBookTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditorBookTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorBookTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

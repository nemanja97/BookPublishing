import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorNotesComponent } from './editor-notes.component';

describe('EditorNotesComponent', () => {
  let component: EditorNotesComponent;
  let fixture: ComponentFixture<EditorNotesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditorNotesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorNotesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

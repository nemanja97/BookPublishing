import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorChoicesComponent } from './editor-choices.component';

describe('EditorChoicesComponent', () => {
  let component: EditorChoicesComponent;
  let fixture: ComponentFixture<EditorChoicesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditorChoicesComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorChoicesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

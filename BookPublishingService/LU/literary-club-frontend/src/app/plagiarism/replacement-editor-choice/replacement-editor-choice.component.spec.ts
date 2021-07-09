import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReplacementEditorChoiceComponent } from './replacement-editor-choice.component';

describe('ReplacementEditorChoiceComponent', () => {
  let component: ReplacementEditorChoiceComponent;
  let fixture: ComponentFixture<ReplacementEditorChoiceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReplacementEditorChoiceComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReplacementEditorChoiceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

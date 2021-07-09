import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorVoteComponent } from './editor-vote.component';

describe('EditorVoteComponent', () => {
  let component: EditorVoteComponent;
  let fixture: ComponentFixture<EditorVoteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditorVoteComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorVoteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

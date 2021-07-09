import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlagiarismBlameComponent } from './plagiarism-blame.component';

describe('PlagiarismBlameComponent', () => {
  let component: PlagiarismBlameComponent;
  let fixture: ComponentFixture<PlagiarismBlameComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PlagiarismBlameComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlagiarismBlameComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

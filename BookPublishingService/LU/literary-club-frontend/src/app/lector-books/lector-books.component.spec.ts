import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LectorBooksComponent } from './lector-books.component';

describe('LectorBooksComponent', () => {
  let component: LectorBooksComponent;
  let fixture: ComponentFixture<LectorBooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LectorBooksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LectorBooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

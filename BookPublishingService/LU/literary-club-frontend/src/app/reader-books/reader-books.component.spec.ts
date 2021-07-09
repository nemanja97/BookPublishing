import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReaderBooksComponent } from './reader-books.component';

describe('ReaderBooksComponent', () => {
  let component: ReaderBooksComponent;
  let fixture: ComponentFixture<ReaderBooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReaderBooksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReaderBooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

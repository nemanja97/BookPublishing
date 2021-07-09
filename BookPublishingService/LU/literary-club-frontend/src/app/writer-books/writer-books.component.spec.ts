import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WriterBooksComponent } from './writer-books.component';

describe('WriterBooksComponent', () => {
  let component: WriterBooksComponent;
  let fixture: ComponentFixture<WriterBooksComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WriterBooksComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WriterBooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

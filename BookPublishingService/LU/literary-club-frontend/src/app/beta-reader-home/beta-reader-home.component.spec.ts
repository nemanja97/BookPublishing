import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BetaReaderHomeComponent } from './beta-reader-home.component';

describe('BetaReaderHomeComponent', () => {
  let component: BetaReaderHomeComponent;
  let fixture: ComponentFixture<BetaReaderHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BetaReaderHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BetaReaderHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddBitcoinInfoComponent } from './add-bitcoin-info.component';

describe('AddBitcoinInfoComponent', () => {
  let component: AddBitcoinInfoComponent;
  let fixture: ComponentFixture<AddBitcoinInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddBitcoinInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddBitcoinInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

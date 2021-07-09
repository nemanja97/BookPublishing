import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LectorHomeComponent } from './lector-home.component';

describe('LectorHomeComponent', () => {
  let component: LectorHomeComponent;
  let fixture: ComponentFixture<LectorHomeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ LectorHomeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LectorHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

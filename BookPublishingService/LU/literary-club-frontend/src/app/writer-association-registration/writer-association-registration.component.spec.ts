import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WriterAssociationRegistrationComponent } from './writer-association-registration.component';

describe('WriterAssociationRegistrationComponent', () => {
  let component: WriterAssociationRegistrationComponent;
  let fixture: ComponentFixture<WriterAssociationRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ WriterAssociationRegistrationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WriterAssociationRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

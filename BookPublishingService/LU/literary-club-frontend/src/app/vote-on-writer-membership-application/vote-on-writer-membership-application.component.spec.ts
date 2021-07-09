import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VoteOnWriterMembershipApplicationComponent } from './vote-on-writer-membership-application.component';

describe('VoteOnWriterMembershipApplicationComponent', () => {
  let component: VoteOnWriterMembershipApplicationComponent;
  let fixture: ComponentFixture<VoteOnWriterMembershipApplicationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VoteOnWriterMembershipApplicationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VoteOnWriterMembershipApplicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

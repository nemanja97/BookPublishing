import { TestBed } from '@angular/core/testing';

import { FormsControlService } from './forms-control.service';

describe('FormsControlService', () => {
  let service: FormsControlService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FormsControlService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});

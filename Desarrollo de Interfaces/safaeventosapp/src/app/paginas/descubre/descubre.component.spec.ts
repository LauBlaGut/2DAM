import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { DescubreComponent } from './descubre.component';

describe('DescubreComponent', () => {
  let component: DescubreComponent;
  let fixture: ComponentFixture<DescubreComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [DescubreComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DescubreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

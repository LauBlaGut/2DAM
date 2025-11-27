import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { TarjetaDescubreComponent } from './tarjeta-descubre.component';

describe('TarjetaDescubreComponent', () => {
  let component: TarjetaDescubreComponent;
  let fixture: ComponentFixture<TarjetaDescubreComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [TarjetaDescubreComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(TarjetaDescubreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

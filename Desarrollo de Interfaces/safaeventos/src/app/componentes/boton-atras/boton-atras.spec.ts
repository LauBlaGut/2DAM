import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BotonAtras } from './boton-atras';

describe('BotonAtras', () => {
  let component: BotonAtras;
  let fixture: ComponentFixture<BotonAtras>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BotonAtras]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BotonAtras);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

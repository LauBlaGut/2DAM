import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { SeleccionUsuarioComponent } from './seleccion-usuario.component';

describe('SeleccionUsuarioComponent', () => {
  let component: SeleccionUsuarioComponent;
  let fixture: ComponentFixture<SeleccionUsuarioComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [SeleccionUsuarioComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SeleccionUsuarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

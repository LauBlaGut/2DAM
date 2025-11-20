import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { CrearEventoComponent } from './crear-evento.component';

describe('CrearEventoComponent', () => {
  let component: CrearEventoComponent;
  let fixture: ComponentFixture<CrearEventoComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [CrearEventoComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CrearEventoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

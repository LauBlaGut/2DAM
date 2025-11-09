import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Descubre } from './descubre';

describe('Descubre', () => {
  let component: Descubre;
  let fixture: ComponentFixture<Descubre>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Descubre]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Descubre);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

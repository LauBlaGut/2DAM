import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { MeInteresaComponent } from './me-interesa.component';

describe('MeInteresaComponent', () => {
  let component: MeInteresaComponent;
  let fixture: ComponentFixture<MeInteresaComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [MeInteresaComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(MeInteresaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

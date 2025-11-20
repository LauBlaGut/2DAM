import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { AniadirComentarioComponent } from './aniadir-comentario.component';

describe('AniadirComentarioComponent', () => {
  let component: AniadirComentarioComponent;
  let fixture: ComponentFixture<AniadirComentarioComponent>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      imports: [AniadirComentarioComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AniadirComentarioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import {IonButton, IonIcon} from '@ionic/angular/standalone';


@Component({
  selector: 'app-boton-atras',
  standalone: true,
  imports: [
    IonButton,
    IonIcon
  ],
  templateUrl: './boton-atras.html',
  styleUrl: './boton-atras.css',
})
export class BotonAtras {

  private router = inject(Router);

  irAtras(){
    this.router.navigate(['/']);
  }
}

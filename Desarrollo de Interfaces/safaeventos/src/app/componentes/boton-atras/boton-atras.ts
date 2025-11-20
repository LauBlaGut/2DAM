import { Component } from '@angular/core';
import {IonButton, IonIcon } from '@ionic/angular/standalone';
import { Location } from '@angular/common';

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

  constructor(private location: Location){}

  irAtras(){
    this.location.back();
  }
}

import {Component, inject, OnInit} from '@angular/core';
import {IonButton, IonIcon} from "@ionic/angular/standalone";
import { Location } from '@angular/common';

@Component({
  selector: 'app-boton-atras',
  templateUrl: './boton-atras.component.html',
  styleUrls: ['./boton-atras.component.scss'],
  standalone: true,
  imports: [
    IonButton,
    IonIcon
  ]
})
export class BotonAtrasComponent  {

  private location = inject(Location);

  irAtras() {
    this.location.back();
  }

}

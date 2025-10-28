import { Component, OnInit } from '@angular/core';
import {
  IonCol,
  IonContent,
  IonFabButton, IonHeader,
  IonIcon, IonItem, IonLabel,
  IonList,
  IonRow,
  IonSearchbar,
  IonToolbar
} from "@ionic/angular/standalone";

@Component({
  selector: 'app-busqueda',
  templateUrl: './busqueda.component.html',
  styleUrls: ['./busqueda.component.scss'],
  imports: [
    IonFabButton,
    IonToolbar,
    IonRow,
    IonCol,
    IonSearchbar,
    IonIcon,
    IonContent,
    IonList,
    IonItem,
    IonLabel,
    IonHeader
  ]
})
export class BusquedaComponent implements OnInit {

  constructor() {}

  ngOnInit() {
    console.log('BusquedaComponent inicializado');
  }
}

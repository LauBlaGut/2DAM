import { Component, OnInit } from '@angular/core';
import {IonCol, IonFabButton, IonIcon, IonRow, IonSearchbar, IonToolbar} from "@ionic/angular/standalone";

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
    IonIcon
  ]
})
export class BusquedaComponent  implements OnInit {

  constructor() { }

  ngOnInit() {}

}

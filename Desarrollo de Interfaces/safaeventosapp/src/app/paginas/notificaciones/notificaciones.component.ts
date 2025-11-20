import { Component, OnInit } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import {NavbarComponent} from "../../componentes/navbar/navbar.component";

export enum TipoNotificacion {
  Favorito = 'favorito',
  Alerta = 'alerta',
}

@Component({
  selector: 'app-notificaciones',
  imports: [IonicModule, NavbarComponent],
  templateUrl: './notificaciones.component.html',
  styleUrls: ['./notificaciones.component.scss'],
  standalone: true,
})
export class NotificacionesComponent  {

  notificaciones = [
    {
      id: 1,
      tipo: TipoNotificacion.Favorito,
      usuario: 'Manuel Miranda',
      mensaje: 'marcó como favorito tu post.',
    },
    {
      id: 2,
      tipo: TipoNotificacion.Alerta,
      mensaje:
        'Disney Karaoke cambia su ubicación, se celebrará en el aula de Música.',
    },
    {
      id: 3,
      tipo: TipoNotificacion.Favorito,
      usuario: 'Manuel Miranda',
      mensaje: 'marcó como favorito tu post',
    },
    {
      id: 4,
      tipo: TipoNotificacion.Alerta,
      mensaje:
        'III Edición de la Feria del Libro ha sido aplazada para el 15 de Diciembre.',
    },
    {
      id: 5,
      tipo: TipoNotificacion.Favorito,
      usuario: 'Manuel Miranda',
      mensaje: 'marcó como favorito tu post',
    },
    {
      id: 6,
      tipo: TipoNotificacion.Alerta,
      mensaje:
        'III Edición de la Feria del Libro ha sido aplazada para el 15 de Diciembre.',
    },
    {
      id: 7,
      tipo: TipoNotificacion.Favorito,
      usuario: 'Manuel Miranda',
      mensaje: 'marcó como favorito tu post',
    },
    {
      id: 8,
      tipo: TipoNotificacion.Alerta,
      mensaje:
        'III Edición de la Feria del Libro ha sido aplazada para el 15 de Diciembre.',
    },
  ];

}

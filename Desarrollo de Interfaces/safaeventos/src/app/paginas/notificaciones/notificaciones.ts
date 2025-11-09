import { Component } from '@angular/core';
import {IonContent, IonHeader, IonIcon, IonItem, IonList, IonTitle, IonToolbar} from '@ionic/angular/standalone';
import {Navbar} from '../../componentes/navbar/navbar';
import {NgClass} from '@angular/common';
import { CommonModule} from '@angular/common';

export enum TipoNotificacion {
  Favorito = 'favorito',
  Alerta = 'alerta',
}

@Component({
  selector: 'app-notificaciones',
  imports: [
    IonHeader,
    Navbar,
    IonTitle,
    IonToolbar,
    IonContent,
    IonList,
    IonIcon,
    IonItem,
    NgClass,
    CommonModule
  ],
  templateUrl: './notificaciones.html',
  styleUrl: './notificaciones.css',
})

export class Notificaciones {
  notificaciones = [
    {
      tipo: TipoNotificacion.Favorito,
      usuario: 'Manuel Miranda',
      mensaje: 'marcó como favorito tu post.',
    },
    {
      tipo: TipoNotificacion.Alerta,
      mensaje:
        'Disney Karaoke cambia su ubicación, se celebrará en el aula de Música.',
    },
    {
      tipo: TipoNotificacion.Favorito,
      usuario: 'Manuel Miranda',
      mensaje: 'marcó como favorito tu post',
    },
    {
      tipo: TipoNotificacion.Alerta,
      mensaje:
        'III Edición de la Feria del Libro ha sido aplazada para el 15 de...',
    },
  ];
}

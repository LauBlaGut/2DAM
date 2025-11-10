import { Component } from '@angular/core';
import { Navbar } from '../../componentes/navbar/navbar';
import {
  IonButton,
  IonContent,
  IonDatetime,
  IonIcon,
  ModalController,
  IonToolbar,
  IonTitle,
  IonButtons,
  IonCard,
  IonCardContent, IonHeader
} from '@ionic/angular/standalone';
import { CommonModule, DatePipe } from '@angular/common';
import { ModalGuardados } from './modal-guardados/modal-guardados';


@Component({
  selector: 'app-calendario',
  imports: [
    Navbar,
    IonContent,
    IonDatetime,
    IonButton,
    DatePipe,
    IonIcon,
    CommonModule,
    IonHeader,
    IonTitle,
    IonToolbar
  ],
  templateUrl: './calendario.html',
  styleUrl: './calendario.css',
})

export class Calendario {
  today: string = new Date().toISOString();

  eventos = [
    {fecha: '2025-011-04', nombre: 'Feria de ciencias'},
    {fecha: '2025-11-20', nombre: 'Concurso de matemáticas'},
    {fecha: '2025-11-24', nombre: 'Exposición de Historia'},
  ];

  highlightedDates = this.eventos.map((e) => {
    return {
      date: e.fecha,
      textColor: '#380A45',
      backgroundColor: '#ffc0cb',
      border: '1px solid #380A45',
    };
  });

  constructor(private modalCtrl: ModalController) {
  }

  async abrirModalGuardados() {
    const modal = await this.modalCtrl.create({
      component: ModalGuardados,
    });
    await modal.present();
  }
}

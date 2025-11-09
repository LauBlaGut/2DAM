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
  IonCardContent
} from '@ionic/angular/standalone';
import { CommonModule, DatePipe } from '@angular/common';

@Component({
  selector: 'app-calendario',
  imports: [
    Navbar,
    IonContent,
    IonDatetime,
    IonButton,
    DatePipe,
    IonIcon,
    CommonModule
  ],
  templateUrl: './calendario.html',
  styleUrl: './calendario.css',
})

export class Calendario {

  eventos = [
    {fecha: '2025-04-04', nombre: 'Feria de ciencias'},
    {fecha: '2025-04-20', nombre: 'Concurso de matemáticas'},
    {fecha: '2025-04-24', nombre: 'Exposición de Historia'},
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

@Component({
  selector: 'app-modal-guardados',
  standalone: true,
  imports: [
    CommonModule,
    IonContent,
    IonToolbar,
    IonTitle,
    IonButtons,
    IonButton,
    IonCard,
    IonCardContent,
  ],
  template: `
    <ion-content class="modal-guardados">
      <ion-toolbar color="tertiary">
        <ion-title>Eventos guardados</ion-title>
        <ion-buttons slot="end">
          <ion-button (click)="cerrar()">Cerrar</ion-button>
        </ion-buttons>
      </ion-toolbar>

      <div class="contenido">
        @if (eventos.length > 0) {
          @for (evento of eventos; track evento.id) {
            <ion-card>
              <img [src]="evento.imagen" alt="Imagen del evento" />
              <ion-card-content>
                <h2>{{ evento.titulo }}</h2>
                <p>{{ evento.fecha }} · {{ evento.hora }}</p>
              </ion-card-content>
            </ion-card>
          }
        } @else {
          <p>No hay eventos guardados.</p>
        }
      </div>
    </ion-content>
  `,
  styleUrls: ['./calendario.css'],
})
export class ModalGuardados {
  eventos: any[] = [];

  constructor(private modalCtrl: ModalController) {}

  ionViewWillEnter() {
    const data = localStorage.getItem('eventosGuardados');
    this.eventos = data ? JSON.parse(data) : [];
  }

  cerrar() {
    this.modalCtrl.dismiss();
  }
}


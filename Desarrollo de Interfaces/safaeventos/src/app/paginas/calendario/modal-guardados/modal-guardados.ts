import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {IonContent, IonToolbar, IonTitle, IonCard, IonCardContent, IonHeader} from '@ionic/angular/standalone';
import { ModalController } from '@ionic/angular/standalone';
import {Navbar} from '../../../componentes/navbar/navbar';
import {Router} from '@angular/router';

@Component({
  selector: 'app-modal-guardados',
  standalone: true,
  imports: [
    CommonModule,
    IonContent,
    IonToolbar,
    IonTitle,
    IonCard,
    IonCardContent,
    IonHeader,
    Navbar,
  ],
  templateUrl: './modal-guardados.html',
  styleUrls: ['./modal-guardados.css'],
})
export class ModalGuardados {
  eventos: any[] = [];

  constructor(private modalCtrl: ModalController, private router: Router) {}

  ionViewWillEnter() {
    const data = localStorage.getItem('eventosGuardados');
    this.eventos = data ? JSON.parse(data) : [];
  }

  cerrar() {
    this.modalCtrl.dismiss();
  }

  abrirEvento(id: number) {
    this.modalCtrl.dismiss();
    this.router.navigate(['/evento', id]);
  }
}

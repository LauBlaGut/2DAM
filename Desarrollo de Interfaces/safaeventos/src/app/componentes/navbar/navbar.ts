import { Component } from '@angular/core';
import {IonButton, IonIcon, IonToolbar, NavController} from '@ionic/angular/standalone';
import { CommonModule} from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    IonToolbar,
    IonButton,
    IonIcon,
    CommonModule
  ],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css'],
})
export class Navbar {
  rol: 'organizador' | 'alumno' = 'alumno';

  constructor(private navCtrl: NavController) {}

  goToCalendario() {
    this.navCtrl.navigateForward(['/calendario']);
  }

  goToDescubre() {
    this.navCtrl.navigateForward(['/descubre']);
  }

  goToCrearEvento() {
    this.navCtrl.navigateForward(['/crear-evento']);
  }

  goToNotificaciones() {
    this.navCtrl.navigateForward(['/notificaciones']);
  }

  goToPerfil() {
    this.navCtrl.navigateForward(['/perfil']);
  }
}

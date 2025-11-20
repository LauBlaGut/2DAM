import { Component } from '@angular/core';
import {IonButton, IonHeader, IonIcon, IonToolbar} from '@ionic/angular/standalone';
import { CommonModule} from '@angular/common';
import {Router, Routes} from '@angular/router';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    IonToolbar,
    IonButton,
    IonIcon,
    CommonModule,
    IonHeader
  ],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css'],
})
export class Navbar {
  rol: 'organizador' | 'alumno' = 'alumno';

  constructor(private router: Router) {}

  goToCalendario() {
    this.router.navigate(['/calendario']);
  }

  goToDescubre() {
    this.router.navigate(['/descubre']);
  }

  goToCrearEvento() {
    this.router.navigate(['/crear-evento']);
  }

  goToNotificaciones() {
    this.router.navigate(['/notificaciones']);
  }

  goToPerfil() {
    this.router.navigate(['/perfil']);
  }
}

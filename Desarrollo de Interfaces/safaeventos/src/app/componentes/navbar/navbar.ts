import { Component } from '@angular/core';
import {IonButton, IonHeader, IonIcon, IonToolbar} from '@ionic/angular/standalone';
import { Router } from '@angular/router';
import { CommonModule} from '@angular/common';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [
    IonHeader,
    IonToolbar,
    IonButton,
    IonIcon,
    CommonModule
  ],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
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

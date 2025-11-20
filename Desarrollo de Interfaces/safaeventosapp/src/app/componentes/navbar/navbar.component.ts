import {Component, inject, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {IonButton, IonHeader, IonIcon, IonToolbar} from "@ionic/angular/standalone";


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
  standalone: true,
  imports: [
    IonHeader,
    IonToolbar,
    IonIcon,
    IonButton
  ]
})
export class NavbarComponent  {

  rol: 'organizador' | 'alumno' = 'organizador';

  private router = inject(Router);

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

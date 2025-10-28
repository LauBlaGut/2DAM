import {Component, inject, OnInit} from '@angular/core';
import { Router } from '@angular/router';
//import { UserService } from 'src/app/services/user.service';
import {IonButton, IonButtons, IonHeader, IonIcon, IonToolbar} from "@ionic/angular/standalone";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  imports: [
    IonButtons,
    IonButton,
    IonIcon,
    IonToolbar,
    IonHeader
  ],
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  private router = inject(Router);
  userName: string = 'Laura';
  userInitial: string = '';
  userPhoto: string | null = null;

  ngOnInit() {
    this.setUserDisplay();
  }

  private setUserDisplay() {
    if (this.userPhoto) {
      return;
    }
    if (this.userName) {
      this.userInitial = this.userName.charAt(0).toUpperCase();
    } else {
      this.userInitial = '?';
    }
  }


  goToCalendario() {
    this.router.navigate(['/calendario']);
  }

  goToBuscar() {
    this.router.navigate(['/buscar']);
  }

  goToNotificaciones() {
    this.router.navigate(['/notificaciones']);
  }

  goToPerfil() {
    this.router.navigate(['/perfil']);
  }
}

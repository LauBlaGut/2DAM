import { Component, OnInit } from '@angular/core';
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
  userName: string = '';
  userInitial: string = '';

  constructor(
    // eslint-disable-next-line @angular-eslint/prefer-inject
    private router: Router,
    // eslint-disable-next-line @angular-eslint/prefer-inject
    //private userService: UserService
  ) {}

  /*ngOnInit() {
    this.userService.getUser().subscribe(user => {
      this.userName = user.name;
      this.userInitial = user.name.charAt(0).toUpperCase();
    });
  }*/


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

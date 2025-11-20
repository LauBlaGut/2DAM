import {Component, OnInit} from '@angular/core';
import {IonAvatar, IonButton, IonContent, IonHeader, IonIcon, IonTitle, IonToolbar} from '@ionic/angular/standalone';
import {DatePipe} from '@angular/common';
import {Navbar} from '../../componentes/navbar/navbar';
import { CommonModule} from '@angular/common';
import {AuthService} from '../../servicios/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-perfil',
  imports: [
    IonContent,
    IonAvatar,
    IonButton,
    IonIcon,
    DatePipe,
    Navbar,
    CommonModule,
    IonHeader,
    IonTitle,
    IonToolbar
  ],
  templateUrl: './perfil.html',
  styleUrl: './perfil.css',
})
export class Perfil {
  usuario = {
    nombre: 'Laura Blanco Gutiérrez',
    fechaNacimiento: new Date(1997, 9, 19),
    correo: 'lblancogutierrez@safareyes.es',
    telefono: '666666666',
    curso: '2º DAM',
    foto: 'assets/img/libelula.jpg',
  };

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  cerrarSesion() {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }
}

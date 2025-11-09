import {Component, OnInit} from '@angular/core';
import {IonAvatar, IonButton, IonContent, IonIcon} from '@ionic/angular/standalone';
import {DatePipe} from '@angular/common';
import {Navbar} from '../../componentes/navbar/navbar';
import { CommonModule} from '@angular/common';

@Component({
  selector: 'app-perfil',
  imports: [
    IonContent,
    IonAvatar,
    IonButton,
    IonIcon,
    DatePipe,
    Navbar,
    CommonModule
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
}

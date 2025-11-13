import { Component, inject } from '@angular/core';
import {CommonModule} from '@angular/common';
import { Router } from '@angular/router';
import {Logo} from '../../componentes/logo/logo';
import {IonContent, IonButton, NavController} from '@ionic/angular/standalone';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [CommonModule, Logo,IonContent, IonButton],
  templateUrl: './inicio.html',
  styleUrl: './inicio.css',
})
export class Inicio {
  constructor(private navCtrl: NavController){}

  goToLogin() {
    this.navCtrl.navigateForward(['/login']);
  }

  goToRegistro() {
    this.navCtrl.navigateForward(['/registro']);
  }
}

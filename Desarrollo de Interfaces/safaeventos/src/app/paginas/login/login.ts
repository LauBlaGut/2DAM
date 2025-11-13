import { Component, inject } from '@angular/core';
import {BotonAtras} from '../../componentes/boton-atras/boton-atras';
import {Logo} from '../../componentes/logo/logo';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import { CommonModule } from '@angular/common';
import {IonButton, IonContent, IonInput, IonItem, IonLabel, NavController} from '@ionic/angular/standalone';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    BotonAtras,
    Logo,
    ReactiveFormsModule,
    CommonModule,
    IonItem,
    IonLabel,
    IonInput,
    IonButton,
    IonContent
  ],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  loginForm: FormGroup;
  constructor(private fb: FormBuilder, private navCtrl: NavController) {
    this.loginForm = this.fb.group({
      email: [''],
      password: ['']
    });
  }

  goToCalendario(){
    this.navCtrl.navigateForward(['/calendario']);
  }


}

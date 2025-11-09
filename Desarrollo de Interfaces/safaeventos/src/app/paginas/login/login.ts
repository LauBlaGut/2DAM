import { Component, inject } from '@angular/core';
import {BotonAtras} from '../../componentes/boton-atras/boton-atras';
import {Logo} from '../../componentes/logo/logo';
import {FormBuilder, ReactiveFormsModule} from '@angular/forms';
import { CommonModule } from '@angular/common';
import {IonButton, IonContent, IonInput, IonItem, IonLabel} from '@ionic/angular/standalone';

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
  private fb = inject(FormBuilder);

  loginForm = this.fb.group({
    email: [''],
    password: ['']
  });

}

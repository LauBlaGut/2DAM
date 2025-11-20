import { Component, inject } from '@angular/core';
import {BotonAtras} from '../../componentes/boton-atras/boton-atras';
import {Logo} from '../../componentes/logo/logo';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import { CommonModule } from '@angular/common';
import {IonButton, IonContent, IonInput, IonItem, IonLabel, NavController} from '@ionic/angular/standalone';
import { AuthService } from '../../servicios/auth.service';
import {Router} from '@angular/router';


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

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService
  ) {
    this.loginForm = this.fb.group({
      email: [''],
      password: ['']
    });
  }

  iniciarSesion() {
    const email = this.loginForm.value.email;
    const contrasenia = this.loginForm.value.password;

    this.authService.login(email, contrasenia).subscribe({
      next: () => {
        this.router.navigate(['/calendario']);
      },
      error: () => {
        alert('Correo o contraseña incorrectos.');
      }
    });
  }
}

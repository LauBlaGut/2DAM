import {Component, inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {IonButton, IonContent, IonInput, IonItem, IonLabel} from "@ionic/angular/standalone";
import {BotonAtrasComponent} from "../../componentes/boton-atras/boton-atras.component";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  standalone: true,
  imports: [
    IonButton,
    IonInput,
    IonLabel,
    ReactiveFormsModule,
    IonItem,
    BotonAtrasComponent,
    IonContent
  ]
})
export class LoginComponent {

  private fb = inject(FormBuilder);
  private router = inject(Router);
  /*private authService = inject(AuthService);*/

  loginForm: FormGroup = this.fb.group({
    email: [''],
    password: ['']
  });

  /*login() {
    const { email, password } = this.loginForm.value;

    this.authService.login(email, password).subscribe({
      next: () => {
        this.router.navigate(['/inicio']); // ajusta a tu ruta
      },
      error: () => {
        console.log('Error al iniciar sesión');
      }
    });
  }*/

  iniciarSesion() {
    this.router.navigate(['/calendario']);
  }
}

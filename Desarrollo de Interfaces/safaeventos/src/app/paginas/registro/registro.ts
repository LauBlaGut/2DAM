import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {BotonAtras} from '../../componentes/boton-atras/boton-atras';
import {Logo} from '../../componentes/logo/logo';
import {
  IonButton,
  IonContent,
  IonInput,
  IonItem,
  IonLabel,
  IonSelect,
  IonSelectOption
} from '@ionic/angular/standalone';
import {Router} from '@angular/router';


@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule, BotonAtras, Logo, IonItem, IonContent, IonLabel, IonSelect, IonSelectOption, IonInput, IonButton],
  templateUrl: './registro.html',
  styleUrl: './registro.css',
})
export class Registro {
  registerForm: FormGroup;

  constructor(private fb: FormBuilder, private router: Router) {
    this.registerForm = this.fb.group({
      nombre: [''],
      apellidos:[''],
      email: [''],
      contrasenia: [''],
      confirmarContrasenia: [''],
      curso: [''],
    });
  }

  cursos: string[] = ['1º ESO', '2º ESO', '3º ESO', '4º ESO', '1º BACH', '2º BACH', '1º FP BÁSICA', '2ª FP BÁSICA', '1º CFM', '2º CFM', '1º CFS', '2ºCFS', '1º DAM', '2º DAM', '1º DAW', '2º DAW'];

  goToCalendario(){
    this.router.navigate(['/calendario']);
  }

}

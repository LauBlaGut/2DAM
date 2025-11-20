import {Component, inject, OnInit} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {BotonAtrasComponent} from "../../componentes/boton-atras/boton-atras.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-registro',
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.scss'],
  standalone: true,
  imports: [
    IonicModule,
    ReactiveFormsModule,
    BotonAtrasComponent
  ]
})
export class RegistroComponent  {
  private fb = inject(FormBuilder);
  private router = inject(Router);

  cursos: string[] = [
    '1º ESO',
    '2º ESO',
    '3º ESO',
    '4º ESO',
    '1º Bachillerato',
    '2º Bachillerato',
    '1º DAM',
    '2º DAM'
  ];

  registerForm: FormGroup = this.fb.group({
    nombre: [''],
    apellidos: [''],
    email: [''],
    contrasenia: [''],
    confirmarContrasenia: [''],
    curso: [''],
  });

  registrar() {
    console.log(this.registerForm.value);
    this.router.navigate(['/calendario']);
  }
}

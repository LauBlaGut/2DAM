import { Component, inject } from '@angular/core';
import { IonicModule, ToastController } from '@ionic/angular';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [IonicModule, ReactiveFormsModule, CommonModule],
  templateUrl: './registro.component.html',
  styleUrls: ['./registro.component.scss']
})

export class RegistroComponent {
  private fb = inject(FormBuilder);
  private toastCtrl = inject(ToastController);
  private router = inject(Router);

  registerForm: FormGroup;
  cursos: string[] = ['1º ESO', '2º ESO', '3º ESO', '4º ESO', '1º BACH', '2º BACH', '1º FP BÁSICA', '2ª FP BÁSICA', '1º CFM', '2º CFM', '1º CFS', '2ºCFS', '1º DAM', '2º DAM', '1º DAW', '2º DAW'];

  emailPattern = /^[\w.-]+@[\w.-]*safa[\w.-]*\.[a-zA-Z]{2,}$/;

  constructor() {
    this.registerForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.pattern(this.emailPattern)]],
      contrasenia: ['', [Validators.required, Validators.minLength(6)]],
      confirmarContrasenia: ['', [Validators.required]],
      curso: ['', [Validators.required]]
    });
  }

  // Función de registro
  async registro() {
    const form = this.registerForm.value;

    // Validamos que las contraseñas coincidan
    if (form.contrasenia !== form.confirmarContrasenia) {
      const toast = await this.toastCtrl.create({
        message: 'Las contraseñas no coinciden',
        duration: 2000,
        color: 'danger'
      });
      toast.present();
      return;
    }

    // Validamos que lo demas sea valido
    if (this.registerForm.valid) {
      console.log('Registro:', form);

      const toast = await this.toastCtrl.create({
        message: '¡Registrado con éxito!',
        duration: 2000,
        color: 'success'
      });
      toast.present();

      this.registerForm.reset();
    } else {
      const toast = await this.toastCtrl.create({
        message: 'Por favor completa todos los campos correctamente',
        duration: 2000,
        color: 'danger'
      });
      toast.present();
    }
  }

  irAtras(){
    this.router.navigate(['/']);
  }
}

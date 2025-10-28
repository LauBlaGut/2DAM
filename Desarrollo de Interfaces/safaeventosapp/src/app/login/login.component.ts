import {Component, inject, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {IonicModule} from "@ionic/angular";
import {SessionService} from "../servicio/sesion";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  standalone: true,
  imports: [
    IonicModule,
    ReactiveFormsModule,
  ],
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private sesion = inject(SessionService);
  private route = inject(ActivatedRoute);

  loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required],
    rol: ['alumno', Validators.required]
  });

  rol: string = '';

  ngOnInit() {
    this.rol = this.route.snapshot.queryParamMap.get('rol') || '';
  }

  iniciarSesion() {
    if (this.loginForm.valid) {
      const rol = this.loginForm.value.rol;
      this.sesion.setRol(rol!);
      this.router.navigate(['/menu']);
    }
  }

  irAtras(){
    this.router.navigate(['/']);
  }
}

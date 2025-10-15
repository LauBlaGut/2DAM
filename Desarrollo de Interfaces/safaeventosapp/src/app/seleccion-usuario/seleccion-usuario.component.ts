import {Component, inject, OnInit} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import { Router } from '@angular/router';


@Component({
  selector: 'app-seleccion-usuario',
  standalone: true,
  imports: [IonicModule],
  templateUrl: './seleccion-usuario.component.html',
  styleUrls: ['./seleccion-usuario.component.scss']
})
export class SeleccionUsuarioComponent {

  private router = inject(Router);

  seleccionar(rol: string) {
    if (rol === 'organizador') {
      this.router.navigate(['/login-organizador']);
    } else if (rol === 'alumno') {
      this.router.navigate(['/login-alumno']);
    }
  }

  goInicio(){
    this.router.navigate(['/']);
  }
}

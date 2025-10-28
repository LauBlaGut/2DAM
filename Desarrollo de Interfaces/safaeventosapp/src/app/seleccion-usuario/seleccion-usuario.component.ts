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
    this.router.navigate(['/login'], { queryParams: { rol } });
  }

  goInicio(){
    this.router.navigate(['/']);
  }
}

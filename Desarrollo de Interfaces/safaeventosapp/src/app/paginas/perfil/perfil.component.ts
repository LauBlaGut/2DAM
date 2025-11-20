import {Component, inject, OnInit} from '@angular/core';
import { IonicModule } from '@ionic/angular';
import {Router} from "@angular/router";
import {NavbarComponent} from "../../componentes/navbar/navbar.component";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-perfil',
  imports: [IonicModule, NavbarComponent, DatePipe],
  templateUrl: './perfil.component.html',
  styleUrls: ['./perfil.component.scss'],
  standalone: true,
})
export class PerfilComponent  {

  /*private authService = inject(AuthService);*/
  private router = inject(Router);

  usuario = {
    nombre: 'Laura Blanco Gutiérrez',
    fechaNacimiento: new Date(1997, 9, 19),
    correo: 'lblancogutierrez@safareyes.es',
    telefono: '666666666',
    curso: '2º DAM',
    foto: 'assets/img/libelula.jpg',
  };

  /*cerrarSesion() {
    this.authService.logout();
    this.router.navigateByUrl('/login');
  }*/

    cerrarSesion(){
      this.router.navigate(['/inicio']);
    }

}

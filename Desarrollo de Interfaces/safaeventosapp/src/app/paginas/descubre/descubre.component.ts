import {Component, inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {IonicModule} from "@ionic/angular";
import {NavbarComponent} from "../../componentes/navbar/navbar.component";

@Component({
  selector: 'app-descubre',
  imports: [IonicModule, NavbarComponent],
  templateUrl: './descubre.component.html',
  styleUrls: ['./descubre.component.scss'],
  standalone: true,
})
export class DescubreComponent  {

  private router = inject(Router);

  categorias = ['Todos', 'Música', 'Deporte', 'Cine', 'Cultura', 'Voluntariado'];
  eventos = [
    { id: 1, nombre: 'Feria de la ciencia', fecha: 'Martes 4 Sept.', lugar: 'Pabellón', imagen: 'assets/img/feria-ciencia.jpg', categoria: 'Música' },
    { id: 2, nombre: 'Disney Karaoke', fecha: 'Martes 4 Sept.', lugar: 'Pabellón', imagen: 'assets/img/disney-karaoke.jpeg', categoria: 'Música' },
    { id: 3, nombre: 'Taller de teatro', fecha: 'Martes 4 Sept.', lugar: 'Pabellón', imagen: 'assets/img/teatro.jpg', categoria: 'Cine' },
    { id: 4, nombre: 'Taller de tejido', fecha: 'Martes 4 Sept.', lugar: 'Pabellón', imagen: 'assets/img/tejido.jpg', categoria: 'Deporte' },
  ];

  verEvento(id: number) {
    this.router.navigate(['/evento', id]);
  }

}

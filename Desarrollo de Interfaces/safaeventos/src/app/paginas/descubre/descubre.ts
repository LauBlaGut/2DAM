import { Component } from '@angular/core';
import {Navbar} from '../../componentes/navbar/navbar';
import {
  IonCard, IonCardContent,
  IonCardHeader,
  IonCardTitle,
  IonChip,
  IonContent, IonIcon,
  IonLabel,
  IonSearchbar
} from '@ionic/angular/standalone';
import { CommonModule} from '@angular/common';
import {Router} from '@angular/router';


@Component({
  selector: 'app-descubre',
  imports: [
    Navbar,
    IonContent,
    CommonModule,
    IonSearchbar,
    IonLabel,
    IonChip,
    IonCard,
    IonCardHeader,
    IonCardTitle,
    IonCardContent,
    IonIcon
  ],
  templateUrl: './descubre.html',
  styleUrl: './descubre.css',
})
export class Descubre {
  constructor(private router: Router) {}

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

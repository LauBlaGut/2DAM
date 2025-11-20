import {Component, inject, OnInit} from '@angular/core';
import { IonicModule} from "@ionic/angular";
import {DatePipe} from "@angular/common";
import {NavbarComponent} from "../../componentes/navbar/navbar.component";
import {Router} from "@angular/router";


@Component({
  selector: 'app-calendario',
  templateUrl: './calendario.component.html',
  styleUrls: ['./calendario.component.scss'],
  standalone: true,
  imports: [
    DatePipe,
    IonicModule,
    NavbarComponent
  ]
})
export class CalendarioComponent  {

  private router = inject(Router);

  today: string = new Date().toISOString();

  eventos = [
    { fecha: '2025-11-04', nombre: 'Feria de ciencias' },
    { fecha: '2025-11-20', nombre: 'Concurso de matemáticas' },
    { fecha: '2025-11-24', nombre: 'Exposición de Historia' },
  ];

  highlightedDates = this.eventos.map(e => ({
    date: e.fecha,
    textColor: '#4C87B9',
    backgroundColor: '#ffc0cb',
    border: '3px solid #4C87B9',
  }));



  goToDescubre() {
    this.router.navigate(['/descubre']);
  }

  goToMeInteresa() {
    this.router.navigate(['/me-interesa']);
  }

}

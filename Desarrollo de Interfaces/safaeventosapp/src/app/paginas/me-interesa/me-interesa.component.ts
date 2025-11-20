import {Component, inject, OnInit} from '@angular/core';
import {NavbarComponent} from "../../componentes/navbar/navbar.component";
import {Router} from "@angular/router";
import {IonicModule} from "@ionic/angular";

@Component({
  selector: 'app-me-interesa',
  templateUrl: './me-interesa.component.html',
  styleUrls: ['./me-interesa.component.scss'],
  standalone: true,
  imports: [
    NavbarComponent,
    IonicModule
  ]
})
export class MeInteresaComponent {

  private router = inject(Router);

  eventos: any[] = [];

  ngOnInit() {
    const data = localStorage.getItem('eventosGuardados');
    this.eventos = data ? JSON.parse(data) : [];
  }

  abrirEvento(id: number) {
    this.router.navigate(['/evento', id]);
  }
}


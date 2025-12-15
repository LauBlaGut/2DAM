import {Component, inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AlertController, IonicModule} from "@ionic/angular";
import {NavbarComponent} from "../../componentes/navbar/navbar.component";
import {TarjetaDescubreComponent} from "../../componentes/tarjeta-descubre/tarjeta-descubre.component";
import {EventoService} from "../../servicios/evento.service";
import {CategoriaEvento} from "../../modelos/categoria-evento.enum";
import { EventEmitter, Output } from '@angular/core';


@Component({
  selector: 'app-descubre',
  standalone: true,
  imports: [IonicModule, NavbarComponent, TarjetaDescubreComponent],
  templateUrl: './descubre.component.html',
  styleUrls: ['./descubre.component.scss'],
})
export class DescubreComponent implements OnInit {

  private router = inject(Router);
  private eventoService = inject(EventoService);


  rolUsuario: 'organizador' | 'alumno' = 'organizador';


  categorias = [
    { nombre: 'ACADÉMICO', valor: CategoriaEvento.ACADEMICO },
    { nombre: 'DEPORTES', valor: CategoriaEvento.DEPORTES },
    { nombre: 'CULTURA', valor: CategoriaEvento.CULTURA },
    { nombre: 'TECNOLOGÍA', valor: CategoriaEvento.TECNOLOGIA },
    { nombre: 'OTROS', valor: CategoriaEvento.OTROS }
  ];

  categoriaSeleccionada: string | null = null;
  eventos: any[] = [];

  ngOnInit() {
    this.cargarProximosEventos();
  }

  cargarProximosEventos() {
    this.eventoService.getProximos().subscribe(data => {
      this.eventos = data;
    });
  }

  onEventoEliminado(id: number) {
    this.eventos = this.eventos.filter(e => e.id !== id);
  }

  @Output() eventoEliminado = new EventEmitter<number>();


  filtrarPorCategoria(cat: string) {

    if (this.categoriaSeleccionada === cat) {
      this.categoriaSeleccionada = null;
      this.cargarProximosEventos();
      return;
    }

    this.categoriaSeleccionada = cat;

    this.eventoService.getByCategoria(cat).subscribe(data => {
      this.eventos = data;
    });
  }

}

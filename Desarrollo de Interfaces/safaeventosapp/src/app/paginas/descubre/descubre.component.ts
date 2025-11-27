import {Component, inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {IonicModule} from "@ionic/angular";
import {NavbarComponent} from "../../componentes/navbar/navbar.component";
import {TarjetaDescubreComponent} from "../../componentes/tarjeta-descubre/tarjeta-descubre.component";
import {EventoService} from "../../servicios/evento.service";
import {CategoriaEvento} from "../../modelos/categoria-evento.enum";

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


  categorias = [
    { nombre: 'ACADÉMICO', valor: CategoriaEvento.ACADÉMICO },
    { nombre: 'DEPORTES', valor: CategoriaEvento.DEPORTES },
    { nombre: 'CULTURA', valor: CategoriaEvento.CULTURA },
    { nombre: 'TECNOLOGÍA', valor: CategoriaEvento.TECNOLOGÍA },
    { nombre: 'OTROS', valor: CategoriaEvento.OTROS }
  ];
  categoriaSeleccionada: CategoriaEvento | null = null;
  eventos: any[] = [];

  ngOnInit() {
    this.cargarProximosEventos();
  }

  cargarProximosEventos() {
    this.eventoService.getProximos().subscribe(data => {
      this.eventos = data;
    });
  }

  filtrarPorCategoria(cat: CategoriaEvento) {

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



  verEvento(id: number) {
    this.router.navigate(['/evento', id]);
  }
}

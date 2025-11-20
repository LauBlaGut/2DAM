import {Component, inject, Input, OnInit} from '@angular/core';
import {IonicModule} from "@ionic/angular";
import {ActivatedRoute, Router} from "@angular/router";
import {BotonAtrasComponent} from "../../componentes/boton-atras/boton-atras.component";

@Component({
  selector: 'app-aniadir-comentario',
  templateUrl: './aniadir-comentario.component.html',
  styleUrls: ['./aniadir-comentario.component.scss'],
  standalone: true,
  imports: [IonicModule, BotonAtrasComponent]
})
export class AniadirComentarioComponent  implements OnInit {

  private router = inject(Router);
  private route = inject(ActivatedRoute);

  // Recibimos el id del evento desde la URL
  eventoId!: number;

  modo: 'comentario' | 'foto' | '' = 'comentario';
  contenido: string | null = null;
  fotoSeleccionada: string | null = null;

  usuario = {
    nombre: 'Laura Blanco Gutiérrez',
    foto: 'assets/img/libelula.jpg',
  };

  ngOnInit() {
    this.eventoId = Number(this.route.snapshot.paramMap.get('id'));
  }

  simularFoto() {
    this.fotoSeleccionada = 'assets/img/foto-subida-ejemplo.jpg';
  }

  publicar() {
    // Aquí enviarias al backend o metes en un servicio
    const payload = this.modo === 'foto'
      ? { tipo: 'foto', contenido: this.fotoSeleccionada }
      : { tipo: 'comentario', contenido: this.contenido };

    console.log('PUBLICADO', payload);

    // Tras publicar, volvemos al evento
    this.router.navigate(['/evento', this.eventoId]);
  }
}

import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule, ModalController } from '@ionic/angular';
import {BotonAtras} from '../../../componentes/boton-atras/boton-atras';

@Component({
  selector: 'app-modal-aniadir-comentario',
  standalone: true,
  imports: [CommonModule, IonicModule, BotonAtras],
  templateUrl: './modal-aniadir-comentario.html',
  styleUrls: ['./modal-aniadir-comentario.css'],
})
export class ModalAniadirComentario {
  @Input() modo: 'comentario' | 'foto' | '' = 'comentario';
  contenido: string | null = null;
  fotoSeleccionada: string | null = null;

  usuario = {
    nombre: 'Laura Blanco Gutiérrez',
    foto: 'assets/img/libelula.jpg',
  };

  constructor(public modalCtrl: ModalController) {}

  cerrar() {
    this.modalCtrl.dismiss();
  }

  simularFoto() {
    this.fotoSeleccionada = 'assets/img/foto-subida-ejemplo.jpg';
  }

  publicar() {
    if (this.modo === 'foto' && this.fotoSeleccionada) {
      this.modalCtrl.dismiss({ tipo: 'foto', contenido: this.fotoSeleccionada });
    } else if (this.modo === 'comentario' && this.contenido) {
      this.modalCtrl.dismiss({ tipo: 'comentario', contenido: this.contenido });
    }
  }
}

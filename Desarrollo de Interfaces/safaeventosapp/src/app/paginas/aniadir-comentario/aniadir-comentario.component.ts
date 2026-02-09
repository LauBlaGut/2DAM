import { Component, inject, OnInit } from '@angular/core';
import { IonicModule } from "@ionic/angular";
import { ActivatedRoute, Router } from "@angular/router";
import { BotonAtrasComponent } from "../../componentes/boton-atras/boton-atras.component";

import { Camera, CameraResultType, CameraSource } from '@capacitor/camera';
import {FotoEventoService} from "../../servicios/foto-evento.service";

@Component({
  selector: 'app-aniadir-comentario',
  templateUrl: './aniadir-comentario.component.html',
  styleUrls: ['./aniadir-comentario.component.scss'],
  standalone: true,
  imports: [IonicModule, BotonAtrasComponent]
})
export class AniadirComentarioComponent implements OnInit {

  private router = inject(Router);
  private route = inject(ActivatedRoute);

  private fotoService = inject(FotoEventoService);

  eventoId!: number;
  modo: 'comentario' | 'foto' | '' = 'comentario';
  contenido: string | null = null;

  fotoSeleccionada: string | null = null;
  base64ParaBackend: string | null = null;

  usuario = {
    id: 1,
    nombre: 'Laura Blanco Gutiérrez',
    foto: 'assets/img/libelula.jpg',
  };

  ngOnInit() {
    this.eventoId = Number(this.route.snapshot.paramMap.get('id'));
  }

  async tomarFoto() {
    try {
      const image = await Camera.getPhoto({
        quality: 80,
        allowEditing: false,
        resultType: CameraResultType.DataUrl,
        source: CameraSource.Prompt
      });

      if (image.dataUrl) {
        this.fotoSeleccionada = image.dataUrl;
        this.base64ParaBackend = image.dataUrl;
      }

    } catch (error) {
      console.log('El usuario canceló la foto o hubo un error.', error);
    }
  }

  publicar() {
    if (this.modo === 'foto' && !this.base64ParaBackend) {
      alert('Debes seleccionar una foto primero.');
      return;
    }

    if (this.modo === 'foto') {
      const fotoDTO = {
        rutaFoto: this.base64ParaBackend
      };

      console.log('Enviando foto...');

      this.fotoService.subirFoto(this.eventoId, this.usuario.id, fotoDTO).subscribe({
        next: (response) => {
          console.log('¡Foto subida con éxito!', response);
          this.router.navigate(['/evento', this.eventoId]);
        },
        error: (error) => {
          console.error('Error al subir:', error);
          alert('Error al subir la imagen. Verifica el tamaño o la conexión.');
        }
      });

    } else {
      console.log('Publicar comentario:', this.contenido);
      this.router.navigate(['/evento', this.eventoId]);
    }
  }
}

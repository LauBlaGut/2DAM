import { Component, inject, OnInit } from '@angular/core';
import { IonicModule } from "@ionic/angular";
import { ActivatedRoute, Router } from "@angular/router";
import { BotonAtrasComponent } from "../../componentes/boton-atras/boton-atras.component";

import { Camera, CameraResultType, CameraSource } from '@capacitor/camera';

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

  eventoId!: number;
  modo: 'comentario' | 'foto' | '' = 'comentario';
  contenido: string | null = null;

  fotoSeleccionada: string | null = null;
  base64ParaBackend: string | null = null;

  usuario = {
    nombre: 'Laura Blanco Gutiérrez',
    foto: 'assets/img/libelula.jpg',
  };

  ngOnInit() {
    this.eventoId = Number(this.route.snapshot.paramMap.get('id'));
  }

  async tomarFoto() {
    try {
      const image = await Camera.getPhoto({
        quality: 90,
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
    const payload = this.modo === 'foto'
      ? {
        tipo: 'foto',
        rutaFoto: this.base64ParaBackend
      }
      : {
        tipo: 'comentario',
        contenido: this.contenido
      };

    console.log('ENVIANDO AL BACKEND:', payload);

    this.router.navigate(['/evento', this.eventoId]);
  }
}

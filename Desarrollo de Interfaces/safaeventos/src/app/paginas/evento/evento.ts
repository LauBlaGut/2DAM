import { Component } from '@angular/core';
import { CommonModule} from '@angular/common';
import {Navbar} from '../../componentes/navbar/navbar';
import { ModalController } from '@ionic/angular/standalone';
import {ActivatedRoute} from '@angular/router';
import {IonicModule, ToastController} from '@ionic/angular';

@Component({
  selector: 'app-evento',
  standalone: true,
  imports: [IonicModule, CommonModule, Navbar],
  templateUrl: './evento.html',
  styleUrl: './evento.css',
})
export class Evento {
  id!: number;
  evento: any;
  guardado = false;
  inscrito = true;
  vista: 'comentarios' | 'fotos' = 'comentarios';

  eventos = [
    {
      id: 1,
      titulo: 'Disney Karaoke',
      fecha: 'Martes 4 Septiembre',
      hora: '19:15 h',
      lugar: 'Pabellón',
      precio: 0,
      descripcion: 'Prepárate para una noche mágica llena de música, risas y diversión en el Disney Karaoke para adolescentes. \nUn evento pensado para que cantes tus canciones favoritas de las películas de Disney, desde los clásicos hasta los hits más nuevos. \nPodrás subir al escenario solo o con tus amigos, demostrar tu talento y sentirte parte del mundo Disney por una noche. \nHabrá luces, premios, sorpresas y un ambiente lleno de energía donde lo importante no es cantar perfecto, sino pasarlo increíble y dejar salir tu lado más creativo. \n¡Atrévete a vivir tu momento de estrella!',
      imagen: 'assets/img/disney-karaoke.jpeg'
    },
    {
      id: 2,
      titulo: 'Feria de Ciencia',
      fecha: 'Jueves 6 Octubre',
      hora: '10:15 h',
      lugar: 'Auditorio Central',
      precio: 0,
      descripcion: 'Descubre los experimentos más alucinantes del año.',
      imagen: 'assets/img/feria-ciencia.jpg'
    }
  ];

  comentarios = [
    { id: 1, usuario: 'Laura', texto: '¡Fue increíble!', avatar: 'assets/img/persona1.png' },
    { id: 2, usuario: 'Alex', texto: 'Repetiría sin dudarlo 🎤', avatar: 'assets/img/persona2.png' }
  ];

  fotos  = [
    { id: 1, url: 'assets/img/karaoke1.jpeg', usuario: { nombre: 'Alex', imagen: 'assets/img/persona2.png' }},
    { id: 2, url: 'assets/img/karaoke2.jpg', usuario: { nombre: 'Laura', imagen: 'assets/img/persona1.png' } },
    { id: 3, url: 'assets/img/karaoke3.jpeg', usuario: { nombre: 'Alex', imagen: 'assets/img/persona2.png' } }
  ];

  constructor(private route: ActivatedRoute, private toastController: ToastController, private modalCtrl: ModalController) {}

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.evento = this.eventos.find(e => e.id === this.id);

    this.guardado = this.estaGuardado(this.id);
  }

  /** Alterna entre guardado y eliminado */
  toggleGuardar() {
    if (this.guardado) {
      this.eliminarEvento(this.id);
      this.guardado = false;
    } else {
      this.guardarEvento(this.evento);
      this.guardado = true;
    }
  }

  /** Guarda el evento en localStorage */
  guardarEvento(evento: any) {
    const data = localStorage.getItem('eventosGuardados');
    const eventosGuardados = data ? JSON.parse(data) : [];
    const existe = eventosGuardados.some((e: any) => e.id === evento.id);

    if (!existe) {
      eventosGuardados.push(evento);
      localStorage.setItem('eventosGuardados', JSON.stringify(eventosGuardados));
    }
  }

  /** Elimina un evento del almacenamiento */
  eliminarEvento(id: number) {
    const data = localStorage.getItem('eventosGuardados');
    if (data) {
      const eventos = JSON.parse(data).filter((e: any) => e.id !== id);
      localStorage.setItem('eventosGuardados', JSON.stringify(eventos));
    }
  }

  /** Comprueba si el evento ya está guardado */
  estaGuardado(id: number): boolean {
    const data = localStorage.getItem('eventosGuardados');
    if (!data) return false;
    const eventos = JSON.parse(data);
    return eventos.some((e: any) => e.id === id);
  }

  async mostrarToast(mensaje: string, color: string) {
    const toast = await this.toastController.create({
      message: mensaje,
      duration: 10000,
      position: 'bottom',
      color: color
    });
    await toast.present();
  }

  async inscribirse() {
    if (this.evento.precio > 0) {
      // Redirige a una pasarela de pago (simulada aquí)
      window.location.href = 'https://tu-pasarela-de-pago.com'; // Puedes reemplazar por Stripe o PayPal
    } else {
      // Evento gratuito → mostrar mensaje y marcar como inscrito
      this.inscrito = true;
      const toast = await this.toastController.create({
        message: 'Te has inscrito correctamente',
        duration: 5000,
        color: '#f19edc',
        position: 'bottom',
      });
      await toast.present();
    }
  }

  cambiarVista(v: 'comentarios' | 'fotos') {
    this.vista = v;
  }

  async subirContenido() {
    const modal = await this.modalCtrl.create({
      component: ModalSubirContenido,
      componentProps: {},
      cssClass: 'modal-subir',
    });

    modal.onDidDismiss().then((res) => {
      if (res.data?.tipo === 'comentario' && res.data?.contenido) {
        this.comentarios.push({
          id: Date.now(),
          usuario: 'Tú',
          texto: res.data.contenido,
          avatar: 'assets/img/avatar1.png',
        });
      } else if (res.data?.tipo === 'foto' && res.data?.contenido) {
        this.fotos.push({
          id: Date.now(),
          url: res.data.contenido,
          usuario:{nombre: 'Tú', imagen: 'assets/img/avatar1.png'}
        });
      }
    });

    await modal.present();
  }

}


@Component({
  standalone: true,
  imports: [
    CommonModule,
    IonicModule
  ],
  template: `
    <ion-header [translucent]="true">
      <ion-toolbar color="tertiary">
        <ion-title>Agregar contenido</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content class="modal-subir-content">
      <div class="opciones">
        <ion-button expand="block" color="success" (click)="modo = 'comentario'">
          Escribir comentario
        </ion-button>
        <ion-button expand="block" color="secondary" (click)="modo = 'foto'">
          Subir foto
        </ion-button>
      </div>

      @if (modo === 'comentario') {
        <div class="comentario-box">
          <ion-textarea
            placeholder="Escribe tu comentario..."
            [autoGrow]="true"
            (ionInput)="contenido = $any($event.target).value">
          </ion-textarea>

          <ion-button expand="block" color="tertiary" (click)="enviar()">
            Enviar comentario
          </ion-button>
        </div>
      }

      @if (modo === 'foto') {
        <div class="foto-box">
          <p>Simulación de subida de imagen</p>
          <ion-button color="light" (click)="simularFoto()">📁 Elegir foto</ion-button>
        </div>
      }
    </ion-content>
  `,
})
export class ModalSubirContenido {
  modo: 'comentario' | 'foto' | '' = '';
  contenido: string | null = null;

  constructor(private modalCtrl: ModalController) {}

  simularFoto() {
    // Imagen de ejemplo (simulación)
    this.contenido = 'assets/img/foto-subida-ejemplo.jpg';
    this.modalCtrl.dismiss({ tipo: 'foto', contenido: this.contenido });
  }

  enviar() {
    if (this.contenido && this.modo === 'comentario') {
      this.modalCtrl.dismiss({ tipo: 'comentario', contenido: this.contenido });
    }
  }
}

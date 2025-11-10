import {Component, Input} from '@angular/core';
import { CommonModule} from '@angular/common';
import {Navbar} from '../../componentes/navbar/navbar';
import { ModalController } from '@ionic/angular/standalone';
import {ActivatedRoute} from '@angular/router';
import {IonicModule, ToastController} from '@ionic/angular';
import {ModalAniadirComentario} from './modal-aniadir-comentario/modal-aniadir-comentario';
import {ModalGuardados} from '../calendario/modal-guardados/modal-guardados';

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
  inscrito = false;
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


  async inscribirse() {
    if (this.evento.precio > 0) {
      // Redirige a una pasarela de pago (simulada aquí)
      window.location.href = 'https://tu-pasarela-de-pago.com'; // Puedes reemplazar por Stripe o PayPal
    } else {
      // Evento gratuito → mostrar mensaje y marcar como inscrito
      this.inscrito = true;
    }
  }

  async desinscribirse() {
    this.inscrito = false;
  }

  cambiarVista(v: 'comentarios' | 'fotos') {
    this.vista = v;
  }


  async abrirModalSubirContenido() {
    const modal = await this.modalCtrl.create({
      component: ModalAniadirComentario,
    });
    modal.onDidDismiss().then((res) => {
      if (res.data?.tipo === 'comentario') {
        this.comentarios.push({
          id: Date.now(),
          usuario: 'Tú',
          texto: res.data.contenido,
          avatar: 'assets/img/avatar1.png',
        });
      } else if (res.data?.tipo === 'foto') {
        this.fotos.push({
          id: Date.now(),
          url: res.data.contenido,
          usuario: { nombre: 'Tú', imagen: 'assets/img/avatar1.png' },
        });
      }
    });

    await modal.present();
  }
}

import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ModalController, IonicModule} from "@ionic/angular";
import {NavbarComponent} from "../../componentes/navbar/navbar.component";

@Component({
  selector: 'app-evento',
  imports: [IonicModule, NavbarComponent],
  templateUrl: './evento.component.html',
  styleUrls: ['./evento.component.scss'],
  standalone: true,
})
export class EventoComponent  {

  private route = inject(ActivatedRoute);
  private router = inject(Router);

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
      descripcion:
        'Prepárate para una noche mágica llena de música, risas y diversión en el Disney Karaoke...',
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

  fotos = [
    { id: 1, url: 'assets/img/karaoke1.jpeg', usuario: { nombre: 'Alex', imagen: 'assets/img/persona2.png' }},
    { id: 2, url: 'assets/img/karaoke2.jpg', usuario: { nombre: 'Laura', imagen: 'assets/img/persona1.png' }},
    { id: 3, url: 'assets/img/karaoke3.jpeg', usuario: { nombre: 'Alex', imagen: 'assets/img/persona2.png' }}
  ];

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.evento = this.eventos.find(e => e.id === this.id);

    this.guardado = this.estaGuardado(this.id);
  }


  toggleGuardar() {
    if (this.guardado) {
      this.eliminarEvento(this.id);
      this.guardado = false;
    } else {
      this.guardarEvento(this.evento);
      this.guardado = true;
    }
  }

  guardarEvento(evento: any) {
    const data = localStorage.getItem('eventosGuardados');
    const eventosGuardados = data ? JSON.parse(data) : [];

    if (!eventosGuardados.some((e: any) => e.id === evento.id)) {
      eventosGuardados.push(evento);
      localStorage.setItem('eventosGuardados', JSON.stringify(eventosGuardados));
    }
  }

  eliminarEvento(id: number) {
    const data = localStorage.getItem('eventosGuardados');
    if (!data) return;

    const eventos = JSON.parse(data).filter((e: any) => e.id !== id);
    localStorage.setItem('eventosGuardados', JSON.stringify(eventos));
  }

  estaGuardado(id: number): boolean {
    const data = localStorage.getItem('eventosGuardados');
    if (!data) return false;

    const eventos = JSON.parse(data);
    return eventos.some((e: any) => e.id === id);
  }



  async inscribirse() {
    if (this.evento.precio > 0) {
      window.location.href = 'https://tu-pasarela-de-pago.com';
      return;
    }

    this.inscrito = true;
  }

  async desinscribirse() {
    this.inscrito = false;
  }


  cambiarVista(v: 'comentarios' | 'fotos') {
    this.vista = v;
  }

  goToSubirContenido() {
    this.router.navigate(['/aniadir-comentario']);
  }
}

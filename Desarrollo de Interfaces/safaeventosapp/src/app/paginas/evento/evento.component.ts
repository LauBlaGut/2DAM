import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IonicModule, ViewWillEnter } from '@ionic/angular';
import { NavbarComponent } from '../../componentes/navbar/navbar.component';
import { EventoService } from '../../servicios/evento.service';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import {DatePipe, SlicePipe} from "@angular/common";
import {EventoCrear} from "../../modelos/EventoCrear";
import { FotoEventoService } from '../../servicios/foto-evento.service';


@Component({
  selector: 'app-evento',
  standalone: true,
  imports: [IonicModule, NavbarComponent, ReactiveFormsModule, SlicePipe, DatePipe],
  templateUrl: './evento.component.html',
  styleUrls: ['./evento.component.scss'],
})
export class EventoComponent implements OnInit {

  private route = inject(ActivatedRoute);
  private eventoService = inject(EventoService);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private fotoService = inject(FotoEventoService);

  id!: number;
  evento: any;

  guardado = false;
  inscrito = false;
  vista: 'comentarios' | 'fotos' = 'comentarios';

  form!: FormGroup;
  modoEdicion = false;

  comentarios = [
    {
      id: 1,
      usuario: 'Alba García',
      avatar: 'https://cdn-icons-png.flaticon.com/512/4140/4140037.png',
      texto: 'El evento estuvo genial, aprendí muchísimo.'
    },
    {
      id: 2,
      usuario: 'Mario López',
      avatar: 'https://cdn-icons-png.flaticon.com/512/4140/4140027.png',
      texto: 'Muy bien organizado, repetiría sin duda.'
    },
    {
      id: 3,
      usuario: 'Lucía Serrano',
      avatar: 'https://cdn-icons-png.flaticon.com/512/4140/4140046.png',
      texto: 'Las actividades fueron muy interesantes.'
    }
  ];

  fotos: any[] = [];

  ngOnInit() {
    this.form = this.fb.group({
      titulo: [''],
      descripcion: [''],
      fecha: [''],
      ubicacion: [''],
      precio: [0],
      categoria: [''],
      foto: ['']
    });

    this.id = Number(this.route.snapshot.paramMap.get('id'));

    // Cargar información del evento
    if (this.id) {
      this.cargarEvento();
      this.guardado = this.estaGuardado(this.id);
    }

    this.route.queryParams.subscribe(params => {
      this.modoEdicion = params['editar'] === 'true';
    });
  }

  ionViewWillEnter() {
    if (this.id) {
      this.cargarFotos();
    }
  }

  cargarFotos() {
    this.fotoService.getByEvento(this.id).subscribe({
      next: (data) => {
        this.fotos = data;
        console.log('Fotos cargadas:', this.fotos);
      },
      error: (err) => console.error('Error cargando fotos', err)
    });
  }

  cargarEvento() {
    this.eventoService.getById(this.id).subscribe(evento => {
      this.evento = evento;

      const fechaHora = `${evento.fecha}T${evento.hora}`;

      this.form.patchValue({
        titulo: evento.titulo,
        descripcion: evento.descripcion,
        fecha: fechaHora,
        ubicacion: evento.ubicacion,
        precio: evento.precio,
        categoria: evento.categoria,
        foto: evento.foto
      });
    });
  }

  guardarEvento(evento: any) {
    const data = localStorage.getItem('eventosGuardados');
    const eventosGuardados = data ? JSON.parse(data) : [];

    if (!eventosGuardados.some((e: any) => e.id === evento.id)) {
      eventosGuardados.push(evento);
      localStorage.setItem('eventosGuardados', JSON.stringify(eventosGuardados));
      this.guardado = true;
    }
  }

  eliminarEvento(id: number) {
    const data = localStorage.getItem('eventosGuardados');
    if (!data) return;

    const eventos = JSON.parse(data).filter((e: any) => e.id !== id);
    localStorage.setItem('eventosGuardados', JSON.stringify(eventos));
    this.guardado = false;
  }

  estaGuardado(id: number): boolean {
    const data = localStorage.getItem('eventosGuardados');
    if (!data) return false;

    return JSON.parse(data).some((e: any) => e.id === id);
  }

  inscribirse() {
    if (this.evento?.precio > 0) {
      window.location.href = 'https://tu-pasarela-de-pago.com';
      return;
    }
    this.inscrito = true;
  }

  desinscribirse() {
    this.inscrito = false;
  }

  cambiarVista(v: 'comentarios' | 'fotos') {
    this.vista = v;
  }

  goToSubirContenido() {
    this.router.navigate(['/aniadir-comentario', this.id]);
  }


  toggleGuardar() {
    if (this.guardado) {
      this.eliminarEvento(this.id);
    } else {
      this.guardarEvento(this.evento);
    }
  }

  //Editar

  activarEdicion() {
    this.modoEdicion = true;
  }

  cancelarEdicion() {
    this.modoEdicion = false;
    this.cargarEvento();
  }

  guardarCambios() {

    if (this.form.invalid) return;

    const rawFecha = this.form.value.fecha; // YYYY-MM-DDTHH:mm
    const [fecha, horaCompleta] = rawFecha.split('T');

    const dto: EventoCrear = {
      titulo: this.form.value.titulo,
      descripcion: this.form.value.descripcion,
      fecha,
      hora: horaCompleta.substring(0, 5),
      lugar: this.form.value.ubicacion,
      precio: this.form.value.precio,
      imagen: this.form.value.foto,
      categoria: this.form.value.categoria,
      idUsuario: this.evento.idUsuario
    };

    this.eventoService.actualizarEvento(this.evento.id, dto).subscribe({
      next: eventoActualizado => {
        this.evento = eventoActualizado;
        this.modoEdicion = false;
      },
      error: err => console.error('Error al actualizar evento', err)
    });
  }

}



import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { NavbarComponent } from '../../componentes/navbar/navbar.component';
import { EventoService } from '../../servicios/evento.service';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import {DatePipe, SlicePipe} from "@angular/common";
import {EventoCrear} from "../../modelos/EventoCrear";


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

  fotos = [
    {
      id: 1,
      url: 'https://images.pexels.com/photos/256369/pexels-photo-256369.jpeg',
      usuario: {
        nombre: 'Daniel',
        imagen: 'https://cdn-icons-png.flaticon.com/512/4140/4140043.png'
      }
    },
    {
      id: 2,
      url: 'https://images.pexels.com/photos/1763075/pexels-photo-1763075.jpeg',
      usuario: {
        nombre: 'Sara',
        imagen: 'https://cdn-icons-png.flaticon.com/512/4140/4140031.png'
      }
    },
    {
      id: 3,
      url: 'https://images.pexels.com/photos/399187/pexels-photo-399187.jpeg',
      usuario: {
        nombre: 'Óscar',
        imagen: 'https://cdn-icons-png.flaticon.com/512/4140/4140039.png'
      }
    }
  ];

  ngOnInit() {
    // Crear el formulario
    this.form = this.fb.group({
      titulo: [''],
      descripcion: [''],
      fecha: [''],
      ubicacion: [''],
      precio: [0],
      categoria: [''],
      foto: ['']
    });

    // Obtener el ID desde la ruta
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    if (!this.id) return;

    //Detectar si viene en modo edición
    this.route.queryParams.subscribe(params => {
      this.modoEdicion = params['editar'] === 'true';
    });

    // Cargar el evento
    this.cargarEvento();

    // Estado guardado
      this.guardado = this.estaGuardado(this.id);
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



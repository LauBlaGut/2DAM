import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { IonicModule, ModalController } from '@ionic/angular'; // Añadido ModalController
import { NavbarComponent } from '../../componentes/navbar/navbar.component';
import { EventoService } from '../../servicios/evento.service';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { DatePipe, SlicePipe } from "@angular/common";
import { EventoCrear } from "../../modelos/EventoCrear";
import { FotoEventoService } from '../../servicios/foto-evento.service';
// @ts-ignore
import { QrCodeModalComponent } from './qr-code-modal/qr-code-modal.component';

@Component({
  selector: 'app-evento',
  standalone: true,
  imports: [QrCodeModalComponent, IonicModule, NavbarComponent, ReactiveFormsModule, SlicePipe, DatePipe],
  templateUrl: './evento.component.html',
  styleUrls: ['./evento.component.scss'],
})
export class EventoComponent implements OnInit {
  // Uso de inject para todo
  private route = inject(ActivatedRoute);
  private eventoService = inject(EventoService);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private fotoService = inject(FotoEventoService);
  private modalCtrl = inject(ModalController); // Inyectado para el QR

  id!: number;
  evento: any;
  eventoUrl: string = '';

  guardado = false;
  inscrito = false;
  vista: 'comentarios' | 'fotos' = 'comentarios';

  form!: FormGroup;
  modoEdicion = false;

  comentarios = [
    { id: 1, usuario: 'Alba García', avatar: 'https://cdn-icons-png.flaticon.com/512/4140/4140037.png', texto: 'El evento estuvo genial!' },
    { id: 2, usuario: 'Mario López', avatar: 'https://cdn-icons-png.flaticon.com/512/4140/4140027.png', texto: 'Muy bien organizado.' }
  ];

  fotos: any[] = [];

  ngOnInit() {
    this.id = Number(this.route.snapshot.paramMap.get('id'));

    // Definimos la URL que tendrá el QR
    this.eventoUrl = `https://safaeventos-angular.onrender.com/evento/${this.id}`;

    this.form = this.fb.group({
      titulo: [''],
      descripcion: [''],
      fecha: [''],
      ubicacion: [''],
      precio: [0],
      categoria: [''],
      foto: ['']
    });

    if (this.id) {
      this.cargarEvento();
      this.guardado = this.estaGuardado(this.id);
      this.cargarFotos();
    }

    this.route.queryParams.subscribe(params => {
      this.modoEdicion = params['editar'] === 'true';
    });
  }

  // Método para abrir el QR
  async compartirQR() {
    const modal = await this.modalCtrl.create({
      component: QrCodeModalComponent,
      componentProps: {
        qrData: this.eventoUrl
      },
      cssClass: 'modal-qr' // Opcional para estilos
    });
    await modal.present();
  }

  cargarFotos() {
    this.fotoService.getByEvento(this.id).subscribe({
      next: (data) => this.fotos = data,
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

  toggleGuardar() {
    const data = localStorage.getItem('eventosGuardados');
    let eventosGuardados = data ? JSON.parse(data) : [];

    if (this.guardado) {
      eventosGuardados = eventosGuardados.filter((e: any) => e.id !== this.id);
      this.guardado = false;
    } else {
      eventosGuardados.push(this.evento);
      this.guardado = true;
    }
    localStorage.setItem('eventosGuardados', JSON.stringify(eventosGuardados));
  }

  estaGuardado(id: number): boolean {
    const data = localStorage.getItem('eventosGuardados');
    return data ? JSON.parse(data).some((e: any) => e.id === id) : false;
  }

  inscribirse() {
    if (this.evento?.precio > 0) {
      window.location.href = 'https://tu-pasarela-de-pago.com';
      return;
    }
    this.inscrito = true;
  }

  desinscribirse() { this.inscrito = false; }
  cambiarVista(v: 'comentarios' | 'fotos') { this.vista = v; }
  goToSubirContenido() { this.router.navigate(['/aniadir-comentario', this.id]); }
  cancelarEdicion() { this.modoEdicion = false; this.cargarEvento(); }

  guardarCambios() {
    if (this.form.invalid) return;
    const rawFecha = this.form.value.fecha;
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
      next: (eventoActualizado) => {
        this.evento = eventoActualizado;
        this.modoEdicion = false;
      },
      error: err => console.error('Error al actualizar', err)
    });
  }
}

import {Component, inject} from '@angular/core';
import {IonicModule} from '@ionic/angular';
import {FormBuilder, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {NavbarComponent} from "../../componentes/navbar/navbar.component";
import {EventoService} from "../../servicios/evento.service";

@Component({
  selector: 'app-crear-evento',
  templateUrl: './crear-evento.component.html',
  styleUrls: ['./crear-evento.component.scss'],
  standalone: true,
  imports: [IonicModule, NavbarComponent, ReactiveFormsModule]
})
export class CrearEventoComponent  {

  private fb = inject(FormBuilder);
  private router = inject(Router);
  private eventoService = inject(EventoService);



  eventoForm = this.fb.group({
    titulo: ['', Validators.required],
    fecha: ['', Validators.required],
    lugar: ['', Validators.required],
    precio: [0, [Validators.required, Validators.min(0)]],
    categoria: ['', Validators.required],
    descripcion: ['', Validators.required],
    fotoUrl: ['', Validators.required]
  });


  crearEvento() {
    if (this.eventoForm.invalid) {
      this.eventoForm.markAllAsTouched();
      return;
    }

    // Fecha ISO → separar fecha y hora
    const rawFecha = this.eventoForm.value.fecha!;
    const date = new Date(rawFecha);
    const fecha = date.toISOString().split('T')[0];
    const hora = date.toLocaleTimeString('es-ES', {
      hour: '2-digit',
      minute: '2-digit',
      hour12: false
    });

    // DTO para enviar al backend
    const evento = {
      titulo: this.eventoForm.value.titulo!,
      descripcion: this.eventoForm.value.descripcion!,
      fecha: fecha,
      hora: hora,
      ubicacion: this.eventoForm.value.lugar!,
      precio: this.eventoForm.value.precio!,
      categoria: this.eventoForm.value.categoria!,
      foto: this.eventoForm.value.fotoUrl!,
      idOrganizador: 1
    };

    console.log("Enviando al backend:", evento);

    this.eventoService.crearEvento(evento).subscribe({
      next: (res) => {
        console.log("Evento guardado:", res);
        this.router.navigate(['/calendario']);
      },
      error: (err) => console.error("Error:", err)
    });
  }
}

import {Component, inject} from '@angular/core';
import {IonicModule} from '@ionic/angular';
import {FormBuilder, ReactiveFormsModule} from "@angular/forms";
import {Router} from "@angular/router";
import {NavbarComponent} from "../../componentes/navbar/navbar.component";

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

  imagenPreview: string | null = null;

  eventoForm = this.fb.group({
    titulo: [''],
    fecha: [''],
    hora: [''],
    lugar: [''],
    precio: [0],
    descripcion: [''],
    imagen: ['']
  });

  seleccionarImagen(event: any) {
    const file = event.target.files?.[0];
    if (!file) return;

    const reader = new FileReader();
    reader.onload = () => {
      this.imagenPreview = reader.result as string;
      this.eventoForm.patchValue({ imagen: this.imagenPreview });
    };

    reader.readAsDataURL(file);
  }

  crearEvento() {
    if (this.eventoForm.invalid) return;

    console.log("EVENTO CREADO:", this.eventoForm.value);

    this.router.navigate(['/calendario']);
  }
}

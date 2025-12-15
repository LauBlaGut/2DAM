import {Component, EventEmitter, inject, Input, Output} from '@angular/core';
import {AlertController, IonicModule} from '@ionic/angular';
import {Router} from "@angular/router";
import {DatePipe} from "@angular/common";
import {EventoService} from "../../servicios/evento.service";

@Component({
  selector: 'app-tarjeta-descubre',
  standalone: true,
  imports: [IonicModule, DatePipe],
  templateUrl: './tarjeta-descubre.component.html',
  styleUrls: ['./tarjeta-descubre.component.scss']
})
export class TarjetaDescubreComponent {

  @Input() evento: any;

  @Input() rol: string | null = null;

  @Output() eventoEliminado = new EventEmitter<number>();

  private router = inject(Router);
  private alertController = inject(AlertController);
  private eventoService = inject(EventoService);



  verEvento(id: number) {
    this.router.navigate(['/evento', id]);
  }

  editarEvento(id: number) {
    this.router.navigate(['/evento', id], {
      queryParams: { editar: 'true' }
    });
  }

  eliminarEvento(id: number) {
    this.eventoService.eliminarEvento(id).subscribe({
      next: () => {
        console.log('Evento eliminado');
        //recargar lista
        this.eventoEliminado.emit(id);      },
      error: err => {
        console.error('Error al eliminar', err);
      }
    });
  }


  async confirmarEliminacion(id: number) {
    let alert = await this.alertController.create({
      header: '¿Está seguro de que desea eliminar este evento?',
      cssClass: 'alert-app',
      buttons: [
        {
          text: 'Sí',
          role: 'destructive',
          handler: () => {
            this.eliminarEvento(id);
          }
        },
        {
          text: 'No',
          role: 'cancel',
          cssClass: 'secondary'
        }
      ]
    })
    await alert.present();
  }

  formatearHora(fecha: string, hora: string): string {
    if (!fecha || !hora) return '';

    const fechaHora = new Date(`${fecha}T${hora}`);
    return fechaHora.toLocaleTimeString('es-ES', {
      hour: '2-digit',
      minute: '2-digit'
    });
  }
}

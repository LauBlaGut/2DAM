import { Component, Input, inject } from '@angular/core';
import { IonicModule, ModalController } from '@ionic/angular';
import { BotonAtrasComponent } from '../boton-atras/boton-atras.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-qr-modal',
  standalone: true,
  imports: [IonicModule, BotonAtrasComponent, CommonModule],
  template: `
    <ion-header class="ion-no-border">
      <ion-toolbar class="mi-toolbar">
        <ion-buttons slot="start">
          <div (click)="cerrar()" style="display: flex; align-items: center; cursor: pointer; padding: 10px;">
            <app-boton-atras></app-boton-atras>
          </div>
        </ion-buttons>
        <ion-title class="mi-titulo">{{ titulo }}</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content class="mi-contenido">
      <div class="contenedor-centrado">
        <h3 class="texto-instruccion">Escanea para ver el evento</h3>

        <div class="qr-card">
          @if (qrData) {
            <img
              [src]="'https://api.qrserver.com/v1/create-qr-code/?size=250x250&data=' + qrData + '&color=380A45'"
              alt="Código QR"
              style="width: 250px; height: 250px;"
            />
          } @else {
            <ion-spinner name="crescent" color="primary"></ion-spinner>
          }
        </div>

        <p class="qr-link">{{ qrData }}</p>
      </div>
    </ion-content>
  `,
  styles: [`
    .mi-toolbar {
      --background: #380A45;
      --color: #f19edc;
      --min-height: 70px;
    }

    .mi-titulo {
      font-family: sans-serif;
      font-size: 1.1rem;
      font-weight: bold;
      color: #f19edc;
    }

    .mi-contenido {
      --background: linear-gradient(180deg, #D4BEE4 0%, #9F75B6 100%);
    }

    .contenedor-centrado {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      height: 100%;
      padding: 20px;
    }

    .texto-instruccion {
      color: #380A45;
      font-size: 1.2rem;
      margin-bottom: 25px;
      font-weight: 600;
    }

    .qr-card {
      background: white;
      padding: 20px;
      border-radius: 25px;
      box-shadow: 0 10px 30px rgba(56, 10, 69, 0.4);
      display: flex;
      justify-content: center;
      align-items: center;
      min-width: 280px;
      min-height: 280px;
    }

    .qr-link {
      margin-top: 25px;
      font-size: 0.8rem;
      color: #380A45;
      opacity: 0.7;
      word-break: break-all;
      text-align: center;
    }
  `]
})
export class QrModalComponent {
  private modalCtrl = inject(ModalController);

  @Input() qrData: string = '';
  @Input() titulo: string = 'Evento';

  cerrar() {
    this.modalCtrl.dismiss();
  }
}

import { Component, Input, inject } from '@angular/core';
import { IonicModule, ModalController } from '@ionic/angular';
import {QRCodeComponent} from 'angularx-qrcode';
import { BotonAtrasComponent } from '../boton-atras/boton-atras.component';

@Component({
  selector: 'app-qr-modal',
  standalone: true,
  imports: [IonicModule, QRCodeComponent, BotonAtrasComponent],
  template: `
    <ion-header class="ion-no-border">
      <ion-toolbar class="mi-toolbar">

        <ion-buttons slot="start">
          <app-boton-atras (click)="cerrar()"></app-boton-atras>
        </ion-buttons>

        <ion-title class="mi-titulo">{{ titulo }}</ion-title>

      </ion-toolbar>
    </ion-header>

    <ion-content class="mi-contenido">
      <div class="contenedor-centrado">
        <h3 class="texto-instruccion">Escanea para ver el evento</h3>

        <div class="qr-card">
          <qrcode
            [qrdata]="qrData"
            [width]="250"
            [errorCorrectionLevel]="'M'"
            [colorDark]="'#380A45'"
            [colorLight]="'#ffffff'">
          </qrcode>
        </div>

        <p class="qr-link">{{ qrData }}</p>
      </div>
    </ion-content>
  `,
  styles: [`
    /* --- 1. CABECERA CORRECTA --- */
    .mi-toolbar {
      --background: #380A45; /* Fondo morado */
      --color: #f19edc;      /* Color base del texto */
      --min-height: 70px;    /* Altura para que respire */
      padding-top: 10px;     /* Ajuste para la barra de estado */
    }

    /* Estilo del título para que se vea como H1 pero dentro de la toolbar */
    .mi-titulo {
      font-family: "TAN Nimbus", sans-serif;
      font-size: 1.3rem;
      letter-spacing: 2px;
      color: #f19edc;
      text-align: left; /* Alineado a la izquierda, cerca del botón */
      padding-left: 0;  /* Eliminar padding extra si es necesario */

      /* Truco para que el título no se corte con "..." si es largo */
      white-space: normal;
      overflow: visible;
      line-height: 1.2;
    }

    /* Ajuste para el botón atrás si es necesario */
    ion-buttons[slot="start"] {
      margin-left: 10px;
    }

    /* --- 2. FONDO DEGRADADO --- */
    .mi-contenido {
      --background: linear-gradient(180deg, #D4BEE4 0%, #9F75B6 100%);
    }

    /* --- 3. CENTRADO --- */
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
      text-align: center;
    }

    /* --- 4. TARJETA QR --- */
    .qr-card {
      background: white;
      padding: 20px;
      border-radius: 25px;
      box-shadow: 0 10px 30px rgba(56, 10, 69, 0.4);
      display: flex;
      justify-content: center;
      align-items: center;
    }

    .qr-link {
      margin-top: 25px;
      font-size: 0.85rem;
      color: #380A45;
      opacity: 0.8;
      text-align: center;
      word-break: break-all;
      max-width: 80%;
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

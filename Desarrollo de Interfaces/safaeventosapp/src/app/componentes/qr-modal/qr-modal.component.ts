import { Component, Input, inject } from '@angular/core';
import { IonicModule, ModalController } from '@ionic/angular';
import { QRCodeComponent } from 'angularx-qrcode';
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
    /* --- 1. CABECERA --- */
    .mi-toolbar {
      --background: #380A45; /* Fondo Morado Oscuro */
      --color: #f19edc;      /* Texto Rosa */
      --min-height: 70px;
      padding-top: 10px;
    }

    .mi-titulo {
      font-family: "TAN Nimbus", sans-serif;
      font-size: 1.2rem;
      white-space: normal;  /* Permite que el texto baje de línea */
      text-overflow: unset; /* Quita los '...' */
      overflow: visible;
      line-height: 1.2;
      text-align: center;
      padding-right: 15px; /* Espacio para equilibrar el botón de atrás */
    }

    /* --- 2. FONDO DEGRADADO --- */
    .mi-contenido {
      /* Degradado de Morado suave a Lila */
      --background: linear-gradient(180deg, #D4BEE4 0%, #9F75B6 100%);
    }

    /* --- 3. CENTRADO PERFECTO --- */
    .contenedor-centrado {
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      min-height: 100%; /* Ocupa toda la altura disponible */
      padding: 20px;
    }

    .texto-instruccion {
      color: #380A45;
      font-size: 1.2rem;
      margin-bottom: 25px;
      font-weight: 600;
      text-align: center;
    }

    /* --- 4. TARJETA DEL QR --- */
    .qr-card {
      background: white;
      padding: 20px;
      border-radius: 25px;
      /* Sombra elegante para que flote sobre el degradado */
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
export class QrCodeModalComponent {
  private modalCtrl = inject(ModalController);

  @Input() qrData: string = '';
  @Input() titulo: string = 'Evento';

  cerrar() {
    this.modalCtrl.dismiss();
  }
}

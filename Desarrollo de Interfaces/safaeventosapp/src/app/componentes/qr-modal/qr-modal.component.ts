import { Component, Input, inject } from '@angular/core';
import { IonicModule, ModalController } from '@ionic/angular';
import { QRCodeComponent } from 'angularx-qrcode'; // Usamos la librería correcta

@Component({
  selector: 'app-qr-code-modal',
  standalone: true,
  imports: [IonicModule, QRCodeComponent],
  template: `
    <ion-header class="ion-no-border">
      <ion-toolbar class="mi-toolbar">
        <ion-title class="mi-titulo">{{ titulo }}</ion-title>
        <ion-buttons slot="end">
          <ion-button (click)="cerrar()">
            <ion-icon name="close-outline" style="color: #f19edc; font-size: 24px;"></ion-icon>
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content class="mi-contenido">
      <div class="contenedor-centrado">
        <h3 class="texto-instruccion">Escanea para ver el evento</h3>

        <div class="qr-marco">
          <qrcode
            [qrdata]="qrData"
            [width]="260"
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
    /* 1. Fondo del Título: Morado oscuro de la app */
    .mi-toolbar {
      --background: #380A45;
      --color: #f19edc; /* Texto rosa */
      --min-height: 70px; /* Un poco más alto para títulos largos */
    }

    /* 2. Estilo para que el título se vea completo */
    .mi-titulo {
      font-family: "TAN Nimbus", sans-serif; /* Tu fuente */
      font-size: 1.2rem;
      white-space: normal; /* Permite que el texto baje de línea */
      text-overflow: unset;
      overflow: visible;
      line-height: 1.2;
      text-align: left;
      padding-right: 10px;
    }

    /* 3. Fondo del contenido: Moradito más claro */
    .mi-contenido {
      --background: #EADCF5; /* Un lila muy suave que combina con tu app */
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
      margin-bottom: 20px;
      font-weight: 600;
    }

    /* Marco blanco o transparente alrededor del QR */
    .qr-marco {
      padding: 15px;
      background: white; /* Recomendado blanco detrás del QR para que la cámara no falle */
      border-radius: 20px;
      box-shadow: 0 4px 15px rgba(56, 10, 69, 0.2); /* Sombra morada suave */
    }

    .qr-link {
      margin-top: 20px;
      font-size: 0.8rem;
      color: #5B356C;
      word-break: break-all;
    }
  `]
})
export class QrModalComponent {
  private modalCtrl = inject(ModalController);

  @Input() qrData: string = '';
  @Input() titulo: string = '';

  cerrar() {
    this.modalCtrl.dismiss();
  }
}

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

})
export class QrModalComponent {
  private modalCtrl = inject(ModalController);

  @Input() qrData: string = '';
  @Input() titulo: string = '';

  cerrar() {
    this.modalCtrl.dismiss();
  }
}

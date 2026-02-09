import { Component, inject, Input } from '@angular/core';
import { IonicModule, ModalController } from '@ionic/angular';
// @ts-ignore
import { QRCodeModule } from 'angularx-qrcode';

@Component({
  selector: 'app-qr-code-modal',
  standalone: true,
  imports: [IonicModule, QRCodeModule],
  template: `
    <ion-header>
      <ion-toolbar color="primary">
        <ion-title>Compartir Evento</ion-title>
        <ion-buttons slot="end">
          <ion-button (click)="cerrar()">
            <ion-icon name="close-outline"></ion-icon>
          </ion-button>
        </ion-buttons>
      </ion-toolbar>
    </ion-header>

    <ion-content class="ion-padding ion-text-center">
      <div class="qr-container">
        <ngx-qrcode
          [elementType]="tipoElemento"
          [value]="qrData"
          cssClass="aclass"
          [errorCorrectionLevel]="nivelCorreccion">
        </ngx-qrcode>
      </div>
      <p style="font-size: 0.8rem; color: gray;">{{ qrData }}</p>
    </ion-content>
  `
})
export class QrCodeModalComponent {
  private modalCtrl = inject(ModalController);
  @Input() qrData!: string;

  // Definimos las variables con los tipos que exige la librería
  tipoElemento = QRCodeModule.CANVAS;
  nivelCorreccion = QRCodeModule.LOW;

  cerrar() {
    this.modalCtrl.dismiss();
  }
}

import { Component, Input, inject } from '@angular/core';
import { IonicModule, ModalController } from '@ionic/angular';
// CAMBIO IMPORTANTE: Importamos el Componente, no el Módulo
import { QRCodeComponent } from 'angularx-qrcode';

@Component({
  selector: 'app-qr-modal',
  standalone: true,
  // CAMBIO IMPORTANTE: Lo ponemos aquí en los imports
  imports: [IonicModule, QRCodeComponent],
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
        <qrcode
          [qrdata]="qrData"
          [width]="256"
          [errorCorrectionLevel]="'M'">
        </qrcode>
      </div>
      <p style="font-size: 0.9rem; color: gray; margin-top: 15px;">
        {{ qrData }}
      </p>
    </ion-content>
  `,
  styles: [`
    .qr-container {
      display: flex;
      justify-content: center;
      margin: 30px 0;
      padding: 10px;
      background: white;
      border-radius: 10px;
      display: inline-block;
    }
  `]
})
export class QrModalComponent {
  private modalCtrl = inject(ModalController);

  @Input() qrData: string = '';

  cerrar() {
    this.modalCtrl.dismiss();
  }
}

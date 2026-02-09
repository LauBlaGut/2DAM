import {Component, inject, OnInit} from '@angular/core';
import {IonicModule, Platform} from "@ionic/angular";
import {DatePipe} from "@angular/common";
import {NavbarComponent} from "../../componentes/navbar/navbar.component";
import {Router} from "@angular/router";
import { BarcodeScanner } from '@capacitor-community/barcode-scanner';
import { ZXingScannerModule } from '@zxing/ngx-scanner';
import { BarcodeFormat } from '@zxing/library';

@Component({
  selector: 'app-calendario',
  templateUrl: './calendario.component.html',
  styleUrls: ['./calendario.component.scss'],
  standalone: true,
  imports: [
    DatePipe,
    IonicModule,
    NavbarComponent,
    ZXingScannerModule
  ]
})
export class CalendarioComponent  {

  private router = inject(Router);
  private platform = inject(Platform);

  // Variables de control
  isScanning: boolean = false;      // Para ocultar el calendario
  isWebScanning: boolean = false;   // Para activar la cámara web
  allowedFormats = [ BarcodeFormat.QR_CODE ];

  today: string = new Date().toISOString();

  eventos = [
    { fecha: '2025-12-04', nombre: 'Feria de ciencias' },
    { fecha: '2025-12-20', nombre: 'Concurso de matemáticas' },
    { fecha: '2025-12-24', nombre: 'Exposición de Historia' },
  ];

  highlightedDates = this.eventos.map(e => ({
    date: e.fecha,
    textColor: '#4C87B9',
    backgroundColor: '#ffc0cb',
    border: '3px solid #4C87B9',
  }));



  goToDescubre() {
    this.router.navigate(['/descubre']);
  }

  goToMeInteresa() {
    this.router.navigate(['/me-interesa']);
  }

  goToScanner() {
    this.router.navigate(['/qr-scanner']);
  }

  async iniciarEscaneo() {
    // 1. Si es MÓVIL (Android/iOS) -> Usamos Capacitor nativo
    if (this.platform.is('capacitor')) {
      await this.escanearNativo();
    }
    // 2. Si es WEB (Navegador) -> Usamos ZXing (Cámara Web)
    else {
      console.log('Modo Web detectado: Activando cámara...');
      this.isWebScanning = true;
    }
  }

  // Lógica nativa (Móvil)
  async escanearNativo() {
    try {
      const status = await BarcodeScanner.checkPermission({ force: true });
      if (status.granted) {
        await BarcodeScanner.hideBackground();
        document.body.classList.add('qr-scanner-active');

        const result = await BarcodeScanner.startScan();

        if (result.hasContent) {
          this.procesarResultado(result.content);
        }
      } else {
        alert('Se necesita permiso de cámara');
      }
    } catch (e) {
      console.error('Error escáner nativo', e);
      this.detenerEscaneo();
    }
  }

  // Lógica Web (Se ejecuta al leer algo con la webcam)
  onWebScanSuccess(resultString: string) {
    this.procesarResultado(resultString);
  }

  // Función común para ambos casos
  procesarResultado(data: string) {
    console.log('QR Detectado:', data);
    this.detenerEscaneo();
    alert('QR Leído: ' + data);
    // Aquí rediriges: this.router.navigate(...)
  }

  detenerEscaneo() {
    this.isWebScanning = false; // Apaga la webcam

    // Limpieza nativa
    if (this.platform.is('capacitor')) {
      BarcodeScanner.showBackground();
      BarcodeScanner.stopScan();
      document.body.classList.remove('qr-scanner-active');
    }
  }
}

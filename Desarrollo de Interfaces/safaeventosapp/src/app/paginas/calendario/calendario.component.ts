import {Component, inject, OnInit} from '@angular/core';
import { IonicModule} from "@ionic/angular";
import {DatePipe} from "@angular/common";
import {NavbarComponent} from "../../componentes/navbar/navbar.component";
import {Router} from "@angular/router";
import { BarcodeScanner } from '@capacitor-community/barcode-scanner';

@Component({
  selector: 'app-calendario',
  templateUrl: './calendario.component.html',
  styleUrls: ['./calendario.component.scss'],
  standalone: true,
  imports: [
    DatePipe,
    IonicModule,
    NavbarComponent
  ]
})
export class CalendarioComponent  {

  private router = inject(Router);

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

  async abrirEscaner() {
    // 1. Pedir permiso de cámara
    const status = await BarcodeScanner.checkPermission({ force: true });

    if (status.granted) {
      // 2. Ocultar la interfaz web para ver la cámara (que está "detrás")
      await BarcodeScanner.hideBackground();
      document.body.classList.add('qr-scanner-active'); // Añadimos clase para transparencia

      // 3. Iniciar escaneo
      const result = await BarcodeScanner.startScan();

      // 4. Si detecta algo...
      if (result.hasContent) {
        console.log('QR encontrado:', result.content);

        // Restaurar la interfaz antes de navegar
        this.detenerEscaner();

        // EJEMPLO: Si el QR es una URL de tu evento, extrae el ID y navega
        // Supongamos que el QR es: "https://tuaweb.com/evento/123"
        // Aquí puedes procesar 'result.content' y navegar
        // this.router.navigate(['/evento', id_extraido]);
        alert('QR Leído: ' + result.content); // Para probar que funciona
      }
    } else {
      alert('Se necesita permiso de cámara');
    }
  }

  detenerEscaner() {
    BarcodeScanner.showBackground();
    BarcodeScanner.stopScan();
    document.body.classList.remove('qr-scanner-active');
  }

}

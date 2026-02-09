import { Component, inject, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule, AlertController, Platform } from '@ionic/angular';
import { Router } from '@angular/router';
import { BarcodeScanner } from '@capacitor-community/barcode-scanner';

@Component({
  selector: 'app-qr-scanner',
  standalone: true,
  imports: [CommonModule, IonicModule], // Importante para que funcione @if y los botones
  templateUrl: './qr-scanner.component.html',
  styleUrls: ['./qr-scanner.component.scss'],
})
export class QrScannerComponent implements OnDestroy {
  // 1. Definir variables (Soluciona "Unresolved variable scanActive")
  public scanActive: boolean = false;

  private alertCtrl = inject(AlertController);
  private platform = inject(Platform);
  private router = inject(Router);

  // 2. Definir la función (Soluciona "Unresolved function startScan")
  async startScan() {
    const status = await BarcodeScanner.checkPermission({ force: true });

    if (status.granted) {
      this.scanActive = true;
      document.querySelector('body')?.classList.add('scanner-active');
      await BarcodeScanner.hideBackground();

      const result = await BarcodeScanner.startScan();

      if (result.hasContent) {
        this.scanActive = false;
        this.handleQrContent(result.content);
        this.stopScan();
      }
    }
  }

  stopScan() {
    BarcodeScanner.showBackground();
    BarcodeScanner.stopScan();
    this.scanActive = false;
    document.querySelector('body')?.classList.remove('scanner-active');
  }

  handleQrContent(content: string) {
    const match = content.match(/\/evento\/(\d+)/);
    if (match && match[1]) {
      this.router.navigate(['/evento', match[1]]);
    }
  }

  ngOnDestroy() {
    this.stopScan();
  }
}

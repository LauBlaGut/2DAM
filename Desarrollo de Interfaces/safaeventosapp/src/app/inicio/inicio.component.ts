import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { IonContent, IonButton } from '@ionic/angular/standalone';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-inicio',
  standalone: true,
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.scss'],
  imports: [CommonModule, IonContent, IonButton],
})
export class InicioComponent {
  private router = inject(Router);

  goToLogin() {
    this.router.navigate(['/seleccion-usuario']);
  }

  goToRegistro() {
    this.router.navigate(['/registro']);
  }
}

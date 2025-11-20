import {Component, inject, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {IonButton, IonContent} from "@ionic/angular/standalone";

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.scss'],
  standalone: true,
  imports: [
    IonButton,
    IonContent
  ]
})
export class InicioComponent  {

  private router = inject(Router);

  goToLogin() {
    this.router.navigate(['/login']);
  }

  goToRegistro() {
    this.router.navigate(['/registro']);
  }

}

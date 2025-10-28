import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { IonicModule } from '@ionic/angular';

@Component({
  selector: 'app-inicio',
  standalone: true,
  imports: [IonicModule],
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.scss'],
})
export class InicioComponent {


  // eslint-disable-next-line @angular-eslint/prefer-inject
  constructor(private router: Router) {}

  goToLogin() {
    this.router.navigate(['/seleccion-usuario']);
  }

  goToRegistro() {
    this.router.navigate(['/registro']);
  }

}

import {Component, inject} from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { CalendarioComponent } from '../calendario/calendario.component';
import { SessionService } from '../servicio/sesion';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-menu',
  standalone: true,
  imports: [IonicModule, CalendarioComponent, NavbarComponent],
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
})
export class MenuComponent {
  private sesion = inject(SessionService);
  rol = this.sesion.getRol();

}

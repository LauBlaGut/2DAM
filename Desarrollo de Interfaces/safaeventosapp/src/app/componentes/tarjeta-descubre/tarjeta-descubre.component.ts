import {Component, Input} from '@angular/core';
import {IonicModule} from '@ionic/angular';

@Component({
  selector: 'app-tarjeta-descubre',
  standalone: true,
  imports: [IonicModule],
  templateUrl: './tarjeta-descubre.component.html',
  styleUrls: ['./tarjeta-descubre.component.scss']
})
export class TarjetaDescubreComponent {

  @Input() evento: any;
}

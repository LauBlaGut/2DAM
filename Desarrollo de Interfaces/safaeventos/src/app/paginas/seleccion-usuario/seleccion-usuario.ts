import { Component } from '@angular/core';
import {IonButton, IonContent} from '@ionic/angular/standalone';
import {Logo} from '../../componentes/logo/logo';
import {BotonAtras} from '../../componentes/boton-atras/boton-atras';
import { CommonModule} from '@angular/common';

@Component({
  selector: 'app-seleccion-usuario',
  standalone: true,
  imports: [
    IonContent,
    IonButton,
    Logo,
    BotonAtras,
    CommonModule
  ],
  templateUrl: './seleccion-usuario.html',
  styleUrl: './seleccion-usuario.css',
})
export class SeleccionUsuario {

}

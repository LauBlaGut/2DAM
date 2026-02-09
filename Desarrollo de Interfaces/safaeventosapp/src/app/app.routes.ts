import { Routes } from '@angular/router';
import {InicioComponent} from "./paginas/inicio/inicio.component";
import {RegistroComponent} from "./paginas/registro/registro.component";
import {LoginComponent} from "./paginas/login/login.component";
import {DescubreComponent} from "./paginas/descubre/descubre.component";
import {CalendarioComponent} from "./paginas/calendario/calendario.component";
import {PerfilComponent} from "./paginas/perfil/perfil.component";
import {NotificacionesComponent} from "./paginas/notificaciones/notificaciones.component";
import {EventoComponent} from "./paginas/evento/evento.component";
import {MeInteresaComponent} from "./paginas/me-interesa/me-interesa.component";
import {AniadirComentarioComponent} from "./paginas/aniadir-comentario/aniadir-comentario.component";
import {CrearEventoComponent} from "./paginas/crear-evento/crear-evento.component";

export const routes: Routes = [
  {
    path: '', component: InicioComponent
  },
  {
    path: 'registro', component: RegistroComponent,
  },
  {
    path: 'login', component: LoginComponent,
  },

  {
    path: 'descubre',
    component: DescubreComponent,
  },
  {
    path: 'calendario',
    component: CalendarioComponent,
  },
  {
    path: 'perfil',
    component: PerfilComponent,
  },
  {
    path: 'notificaciones',
    component: NotificacionesComponent,
  },
  {
    path: 'evento/:id',
    component: EventoComponent,
  },
  {
    path: 'me-interesa',
    component: MeInteresaComponent,
  },
  {
    path: 'crear-evento',
    component: CrearEventoComponent,
  },
  {
    path: 'aniadir-comentario/:id',
    component: AniadirComentarioComponent,
  },

  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full',
  },
  {
    path: 'qr-scanner',
    loadComponent: () => import('./componentes/qr-scanner/qr-scanner.component').then(m => m.QrScannerComponent)
  },
];

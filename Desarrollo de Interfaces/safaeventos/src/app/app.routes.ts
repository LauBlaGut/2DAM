import { Routes } from '@angular/router';
import {Inicio} from './paginas/inicio/inicio';
import {Registro} from './paginas/registro/registro';
import {Login} from './paginas/login/login';
import {Descubre} from './paginas/descubre/descubre';
import {Calendario} from './paginas/calendario/calendario';
import {Perfil} from './paginas/perfil/perfil';
import {Notificaciones} from './paginas/notificaciones/notificaciones';
import {Evento} from './paginas/evento/evento';

export const routes: Routes = [
  {
    path: '', component: Inicio
  },
  {
    path: 'seleccion-usuario',
    loadComponent: () =>
      import('./paginas/seleccion-usuario/seleccion-usuario').then(
        (m) => m.SeleccionUsuario
      ),
  },
  {
    path: 'registro', component: Registro,
  },
  {
    path: 'login', component: Login,
  },
  {
    path: 'descubre', component: Descubre,
  },
  {
    path: 'calendario', component: Calendario,
  },
  {
    path: 'perfil', component: Perfil,
  },
  {
    path: 'notificaciones', component: Notificaciones,
  },
  {
    path: 'evento/:id', component: Evento,
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full',
  },
];


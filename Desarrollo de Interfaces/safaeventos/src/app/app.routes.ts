import { Routes } from '@angular/router';
import {Inicio} from './paginas/inicio/inicio';
import {Registro} from './paginas/registro/registro';
import {Login} from './paginas/login/login';
import {Descubre} from './paginas/descubre/descubre';
import {Calendario} from './paginas/calendario/calendario';
import {Perfil} from './paginas/perfil/perfil';
import {Notificaciones} from './paginas/notificaciones/notificaciones';
import {Evento} from './paginas/evento/evento';
import { authGuard } from './guards/auth.guard';

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
    path: 'descubre',
    component: Descubre,
    canActivate: [authGuard]
  },
  {
    path: 'calendario',
    component: Calendario,
    canActivate: [authGuard]
  },
  {
    path: 'perfil',
    component: Perfil,
    canActivate: [authGuard]
  },
  {
    path: 'notificaciones',
    component: Notificaciones,
    canActivate: [authGuard]
  },
  {
    path: 'evento/:id',
    component: Evento,
    canActivate: [authGuard]
  },

  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full',
  },
];

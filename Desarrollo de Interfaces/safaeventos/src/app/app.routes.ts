import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./paginas/inicio/inicio').then((m) => m.Inicio),
  },
  {
    path: 'seleccion-usuario',
    loadComponent: () =>
      import('./paginas/seleccion-usuario/seleccion-usuario').then(
        (m) => m.SeleccionUsuario
      ),
  },
  {
    path: 'registro',
    loadComponent: () =>
      import('./paginas/registro/registro').then((m) => m.Registro),
  },
  {
    path: 'login',
    loadComponent: () =>
      import('./paginas/login/login').then((m) => m.Login),
  },
  {
    path: 'buscar',
    loadComponent: () =>
      import('./paginas/descubre/descubre').then((m) => m.Descubre),
  },
  {
    path: 'calendario',
    loadComponent: () =>
      import('./paginas/calendario/calendario').then(
        (m) => m.Calendario
      ),
  },
  {
    path: 'perfil',
    loadComponent: () =>
      import('./paginas/perfil/perfil').then((m) => m.Perfil),
  },
  {
    path: 'descubre',
    loadComponent: () =>
      import('./paginas/descubre/descubre').then((m) => m.Descubre),
  },
  {
    path: 'notificaciones',
    loadComponent: () =>
      import('./paginas/notificaciones/notificaciones').then((m) => m.Notificaciones),
  },
  {
    path: 'evento/:id',
    loadComponent: () =>
      import('./paginas/evento/evento').then((m) => m.Evento),
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full',
  },
];


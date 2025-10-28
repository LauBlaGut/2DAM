import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./inicio/inicio.component').then((m) => m.InicioComponent),
  },
  {
    path: 'seleccion-usuario',
    loadComponent: () =>
      import('./seleccion-usuario/seleccion-usuario.component').then(
        (m) => m.SeleccionUsuarioComponent
      ),
  },
  {
    path: 'registro',
    loadComponent: () =>
      import('./registro/registro.component').then((m) => m.RegistroComponent),
  },
  {
    path: 'login',
    loadComponent: () =>
      import('./login/login.component').then((m) => m.LoginComponent),
  },
  {
    path: 'buscar',
    loadComponent: () =>
      import('./busqueda/busqueda.component').then((m) => m.BusquedaComponent),
  },
  {
    path: 'calendario',
    loadComponent: () =>
      import('./calendario/calendario.component').then(
        (m) => m.CalendarioComponent
      ),
  },
  {
    path: '**',
    redirectTo: '',
    pathMatch: 'full',
  },
];

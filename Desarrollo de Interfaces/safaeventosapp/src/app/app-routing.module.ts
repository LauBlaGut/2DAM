import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InicioComponent } from './inicio/inicio.component';


const routes: Routes = [
  {
    path: '',
    component: InicioComponent
  },
  {
    path: 'seleccion-usuario',
    loadComponent: () => import('./seleccion-usuario/seleccion-usuario.component').then(m => m.SeleccionUsuarioComponent)
  },
  {
    path: 'registro',
    loadComponent: () => import('./registro/registro.component').then(m => m.RegistroComponent)
  },
  {
    path: 'login',
    loadComponent: () => import('./login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'menu',
    loadComponent: () => import('./menu/menu.component').then(m => m.MenuComponent)
  }
  /*{ path: 'calendario', component: CalendarioPage },
  { path: 'buscar', component: BuscarPage },
  { path: 'notificaciones', component: NotificacionesPage },
  { path: 'perfil', component: PerfilPage },*/
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule {}

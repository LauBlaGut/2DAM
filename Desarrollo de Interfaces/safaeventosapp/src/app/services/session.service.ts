import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  rol: string = '';

  constructor() { }

  setRol(rol: string) {
    this.rol = rol;
  }

  getRol(): string {
    return this.rol;
  }
}

import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Usuario} from "../modelos/usuario.model";

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private http = inject(HttpClient);
  private baseUrl = 'https://safaeventos-springboot.onrender.com/usuarios';

  getAll(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.baseUrl}/all`);
  }

  getById(id: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.baseUrl}/${id}`);
  }

  guardar(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.baseUrl}`, usuario);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}?id=${id}`);
  }
}

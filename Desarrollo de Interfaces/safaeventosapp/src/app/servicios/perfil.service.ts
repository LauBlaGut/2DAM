import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Perfil} from "../modelos/perfil.model";

@Injectable({ providedIn: 'root' })
export class PerfilService {
  private http = inject(HttpClient);
  private baseUrl = 'https://twodam.onrender.com/perfiles';

  getAll(): Observable<Perfil[]> {
    return this.http.get<Perfil[]>(`${this.baseUrl}/all`);
  }

  getById(id: number): Observable<Perfil> {
    return this.http.get<Perfil>(`${this.baseUrl}/${id}`);
  }

  guardar(perfil: Perfil): Observable<Perfil> {
    return this.http.post<Perfil>(`${this.baseUrl}`, perfil);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}?id=${id}`);
  }
}

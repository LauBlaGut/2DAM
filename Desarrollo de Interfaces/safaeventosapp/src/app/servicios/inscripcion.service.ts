import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Inscripcion} from "../modelos/inscripcion.model";

@Injectable({ providedIn: 'root' })
export class InscripcionService {
  private http = inject(HttpClient);
  private baseUrl = 'https://twodam.onrender.com/inscripciones';

  getAll(): Observable<Inscripcion[]> {
    return this.http.get<Inscripcion[]>(`${this.baseUrl}/all`);
  }

  getByUsuario(id: number): Observable<Inscripcion[]> {
    return this.http.get<Inscripcion[]>(`${this.baseUrl}/usuario/${id}`);
  }

  getByEvento(id: number): Observable<Inscripcion[]> {
    return this.http.get<Inscripcion[]>(`${this.baseUrl}/evento/${id}`);
  }

  guardar(data: Inscripcion): Observable<Inscripcion> {
    return this.http.post<Inscripcion>(`${this.baseUrl}/guardar`, data);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/eliminar/${id}`);
  }
}

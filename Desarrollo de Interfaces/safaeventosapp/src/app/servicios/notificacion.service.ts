import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Notificacion} from "../modelos/notificacion.model";

@Injectable({ providedIn: 'root' })
export class NotificacionService {
  private http = inject(HttpClient);
  private baseUrl = 'https://safaeventos-springboot.onrender.com/notificaciones';

  getAll(): Observable<Notificacion[]> {
    return this.http.get<Notificacion[]>(`${this.baseUrl}/all`);
  }

  getById(id: number): Observable<Notificacion> {
    return this.http.get<Notificacion>(`${this.baseUrl}/${id}`);
  }

  guardar(data: Notificacion): Observable<Notificacion> {
    return this.http.post<Notificacion>(`${this.baseUrl}`, data);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}?id=${id}`);
  }
}

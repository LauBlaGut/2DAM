import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Evento} from "../modelos/evento.model";
import {CategoriaEvento} from "../modelos/categoria-evento.enum";

@Injectable({ providedIn: 'root' })
export class EventoService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/eventos';

  getAll(): Observable<Evento[]> {
    return this.http.get<Evento[]>(`${this.baseUrl}/all`);
  }

  getById(id: number): Observable<Evento> {
    return this.http.get<Evento>(`${this.baseUrl}/${id}`);
  }

  getProximos(): Observable<Evento[]> {
    return this.http.get<Evento[]>(`${this.baseUrl}/proximos`);
  }

  getByCategoria(cat: CategoriaEvento): Observable<Evento[]> {
    return this.http.get<Evento[]>(`${this.baseUrl}/categoria/${cat}`);
  }

  guardar(evento: Evento): Observable<Evento> {
    return this.http.post<Evento>(`${this.baseUrl}`, evento);
  }

  actualizar(id: number, evento: Evento): Observable<Evento> {
    return this.http.put<Evento>(`${this.baseUrl}/${id}`, evento);
  }

  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}?id=${id}`);
  }
}

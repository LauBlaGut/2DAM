import { Injectable } from '@angular/core';

export interface EventModel {
  id: number;
  title: string;
  date: string;
  description?: string;
}

@Injectable({ providedIn: 'root' })
export class EventService {
  getUserEvents(): EventModel[] {
    return [
      { id: 1, title: 'Feria de Ciencias', date: '2025-11-04' },
      { id: 2, title: 'Examen de Matemáticas', date: '2025-11-18' },
      { id: 3, title: 'Exposición de Historia', date: '2025-11-24' }
    ];
  }
}

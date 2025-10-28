import { Component } from '@angular/core';
import { FullCalendarModule } from '@fullcalendar/angular';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import esLocale from '@fullcalendar/core/locales/es';


@Component({
  selector: 'app-calendario',
  standalone: true,
  imports: [FullCalendarModule],
  template: `
    <full-calendar [options]="calendarOptions"></full-calendar>
  `,
  styleUrls: ['./calendario.component.scss'],
})
export class CalendarioComponent {
  calendarOptions = {
    initialView: 'dayGridMonth',
    plugins: [dayGridPlugin, interactionPlugin],
    locale: esLocale,
    events: [
      { title: 'Evento 1', date: '2025-10-25' },
      { title: 'Evento 2', date: '2025-10-28' }
    ],
    editable: true,
    selectable: true,
  };
}

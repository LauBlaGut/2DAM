import { Component, OnInit, inject } from '@angular/core';
import { EventService, EventModel } from '../services/event.service';
import { DatePipe, CommonModule } from '@angular/common';
import {IonButton, IonContent, IonDatetime } from '@ionic/angular/standalone';
import {NavbarComponent} from "../navbar/navbar.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-calendario',
  standalone: true,
  imports: [
    CommonModule,
    DatePipe,
    IonContent,
    IonDatetime,
    IonButton,
    NavbarComponent,
  ],
  templateUrl: './calendario.component.html',
  styleUrls: ['./calendario.component.scss'],
})
export class CalendarioComponent implements OnInit {
  private eventService = inject(EventService);
  private router = inject(Router);

  selectedDate: string = '';
  events: EventModel[] = [];

  ngOnInit() {
    this.events = this.eventService.getUserEvents();
  }

  onDateSelect(event: any) {
    this.selectedDate = event.detail.value.split('T')[0];
  }

  goToEvent(event: EventModel) {
    console.log('Ir al evento:', event);
    // @ts-ignore
    this.router.navigate(['/evento', event.id]);
  }
}

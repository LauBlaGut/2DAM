import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';
import { addIcons } from 'ionicons';
import {
  arrowBackOutline,
  calendarOutline,
  searchOutline,
  notificationsOutline,
  personCircleOutline,
  addCircleOutline,
  cameraOutline,
  starOutline,
  alertCircleOutline,
  locationOutline,
  sparklesOutline,
  cardOutline,
  bookmarkOutline,
  ticketOutline,
  timeOutline,
  bookmark,
  addOutline
} from 'ionicons/icons';

addIcons({
  'arrow-back-outline': arrowBackOutline,
  'calendar-outline': calendarOutline,
  'search-outline': searchOutline,
  'notifications-outline': notificationsOutline,
  'person-circle-outline': personCircleOutline,
  'add-circle-outline': addCircleOutline,
  'camera-outline': cameraOutline,
  'star-outline': starOutline,
  'alert-circle-outline': alertCircleOutline,
  'location-outline': locationOutline,
  'sparkles-outline': sparklesOutline,
  'cardOutline': cardOutline,
  'bookmark-outline': bookmarkOutline,
  'ticket-outline': ticketOutline,
  'time-outline': timeOutline,
  'bookmark': bookmark,
  'add-outline': addOutline
});

bootstrapApplication(App, appConfig)
  .catch((err) => console.error(err));

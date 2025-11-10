import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';
import { addIcons } from 'ionicons';
import * as allIcons from 'ionicons/icons';

addIcons(allIcons);

bootstrapApplication(App, appConfig)
  .catch((err) => console.error(err));

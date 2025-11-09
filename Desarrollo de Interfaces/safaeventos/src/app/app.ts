import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {IonRouterOutlet} from '@ionic/angular/standalone';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, IonRouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('safaeventos');
}

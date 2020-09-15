import {Component, OnInit} from '@angular/core';
import {AEvent} from 'src/app/models/a-event';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  events: AEvent[] = [];

  constructor() {
  }

  ngOnInit(): void {
    this.events.push(
      // @ts-ignore
      new AEvent());
    console.log(this.events.length);
  }
}

import {Component, OnInit} from '@angular/core';
import {AEvent, AEventStatus} from 'src/app/models/a-event';

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
      new AEvent('some event', AEventStatus.DRAFT, new Date(2020, 9, 15)
        , true, new Date(2020, 10, 15)
        , 5, 'helloworld', 10));
    console.log(this.events.length);
  }
}

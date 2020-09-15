import {Component, OnInit} from '@angular/core';
import {AEvent, AEventStatus} from '../../../models/a-event';

@Component({
  selector: 'app-overview1',
  templateUrl: './overview1.component.html',
  styleUrls: ['./overview1.component.css']
})
export class Overview1Component implements OnInit {

  public aEvents: AEvent[];

  constructor() {
  }

  ngOnInit(): void {
    this.aEvents = [];
    for (let i = 0; i < 9; i++) {
      this.addRandomAEvent();
    }
  }

  addRandomAEvent() {
    this.aEvents.push(AEvent.createRandomAEvent());
  }

  handelClick(): void {
    this.addRandomAEvent();
  }
}

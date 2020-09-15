import {Component, OnInit} from '@angular/core';
import {AEvent, AEventStatus} from '../../../models/a-event';

@Component({
  selector: 'app-overview2',
  templateUrl: './overview2.component.html',
  styleUrls: ['./overview2.component.css']
})
export class Overview2Component implements OnInit {
  public aEvents: AEvent [];
  public selectedIndex: number;
  public selectedEvent: AEvent;

  constructor() {
  }

  ngOnInit(): void {
    this.aEvents = [];
    for (let i = 0; i < 9; i++) {
      this.addRandomAEvent();
    }
  }

  onSelect(aIndex): void {
    this.selectedIndex = aIndex;
    console.log(this.aEvents[aIndex]);
    this.selectedEvent = this.aEvents[aIndex];
  }

  handelClick(): void {
    this.addRandomAEvent();
    // this.onSelect(this.aEvents.length);
    for (let i = 0; i < this.aEvents.length; i++) {
      this.onSelect(i);
    }
  }

  addRandomAEvent(): void {
    this.aEvents.push(AEvent.createRandomAEvent());
  }
}


import {Component, OnInit} from '@angular/core';
import {AEvent, AEventStatus} from '../../../models/a-event';

@Component({
  selector: 'app-overview2',
  templateUrl: './overview2.component.html',
  styleUrls: ['./overview2.component.css']
})
export class Overview2Component implements OnInit {
  aEvents: AEvent [];
  selectedIndex: number;
  selectedEvent: AEvent;

  highlightRow: number;
  selectedAEvent = null;
  clickedRow: any;

  constructor() {
    // tslint:disable-next-line:typedef
    this.clickedRow = function(index) {
      this.highlightRow = index;
    };
  }

  // tslint:disable-next-line:typedef
  isSelected(event) {
    this.selectedAEvent = event;

    this.selectedIndex = event;
    console.log(this.aEvents[event]);
    this.selectedEvent = this.aEvents[event];
  }

  ngOnInit(): void {
    this.aEvents = [];
    for (let i = 0; i < 9; i++) {
      this.addRandomAEvent();
    }
  }

  handelClick(): void {
    this.addRandomAEvent();
    // this.onSelect(this.aEvents.length);
    for (let i = 0; i < 9; i++) {
      this.isSelected(i);

      this.highlightRow = i;
    }
  }

  addRandomAEvent(): void {
    this.aEvents.push(AEvent.createRandomAEvent());
  }

  // tslint:disable-next-line:typedef
  deleteClick(event: AEvent) {
    const index = this.aEvents.indexOf(event);

    if (index > -1) {
      this.aEvents.splice(index, 1);

      this.isSelected(null);
    } else {
      return;
    }
  }
}

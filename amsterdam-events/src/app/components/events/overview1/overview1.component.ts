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

  addRandomAEvent(): void {
    const i = new AEvent('some event', AEventStatus.DRAFT, new Date(2020, 9, 15)
      , true, new Date(2020, 10, 15)
      , 5, 'helloworld', 10);
    i.title = 'The Event' + (this.aEvents.length + 1);
    i.start = this.randomDate(new Date(2019, 9, 20), new Date(2019, 10, 29));
    i.end = this.randomDate(i.start, new Date(2019, 10, 30));
    i.status = this.getRandomStatue();
    i.maxParticipants = Math.round(Math.random() * 5000);

    this.aEvents.push(i);
  }

  handelClick(): void {
    this.addRandomAEvent();

  }

  // tslint:disable-next-line:typedef
  randomDate(start: Date, end: Date) {
    return new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
  }

  generateRandomNumber(): void {
    const num = 0;
    const min = 0;
    const max = 20;
    let highlightedNumber;
    highlightedNumber = Math.round((Math.random() * (max - min) + min) * 100) / 100;
    return highlightedNumber;
  }

  getRandomStatue(): AEventStatus {
    const key = Math.floor(Math.random() * Math.floor(3) + 1);
    if (key === 1) {
      return AEventStatus.PUBLISHED;
    }
    if (key === 2) {
      return AEventStatus.CANCELED;
    }
    if (key === 3) {
      return AEventStatus.DRAFT;
    } else {
      return null;
    }
  }
}

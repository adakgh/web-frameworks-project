import {Component, OnInit} from '@angular/core';
import {AEvent} from '../../../models/a-event';
import {AEventsService} from '../../../services/a-events.service';

@Component({
  selector: 'app-overview3',
  templateUrl: './overview3.component.html',
  styleUrls: ['./overview3.component.css']
})
export class Overview3Component implements OnInit {
  selectedAEventId = -1;
  aevents: AEvent[];


  constructor(public aEventsService: AEventsService) {
    this.aevents = aEventsService.findAll();
  }

  onEventSelected(aEvent: AEvent): void {
    this.selectedAEventId = aEvent.id;
  }

  ngOnInit(): void {
  }

  handelClick(): void {
    this.addRandomAEvent();
  }

  addRandomAEvent(): void {
    this.aevents.push(AEvent.createRandomAEvent());
  }
}

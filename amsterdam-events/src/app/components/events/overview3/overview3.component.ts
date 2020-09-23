import {Component, OnInit} from '@angular/core';
import {AEvent} from '../../../models/a-event';
import {AEventsService} from '../../../services/a-events.service';

@Component({
  selector: 'app-overview3',
  templateUrl: './overview3.component.html',
  styleUrls: ['./overview3.component.css']
})
export class Overview3Component implements OnInit {

  public selectedAEventId: number;
  aevents: AEvent[];

  constructor(private aEventsService: AEventsService) {
    this.aevents = aEventsService.findAll();
  }

  onEventSelected(aEvent: AEvent): void {
    this.selectedAEventId = aEvent.id;
  }

  ngOnInit(): void {
  }

}

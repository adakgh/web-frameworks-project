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
  selectedIndex: number;
  selectedEvent: AEvent;

  highlightRow: number;
  selectedAEvent = null;
  clickedRow: any;

  constructor(private aEventsService: AEventsService) {
    this.aevents = aEventsService.findAll();

    // tslint:disable-next-line:typedef
    this.clickedRow = function(index) {
      this.highlightRow = index;
    };
  }

  onEventSelected(aEvent: AEvent): void {
    this.selectedAEventId = aEvent.id;
  }

  // tslint:disable-next-line:typedef
  isSelected(event) {
    this.selectedAEvent = event;
    this.selectedIndex = event;
    this.selectedEvent = this.aevents[event];
  }

  ngOnInit(): void {
  }

  handelClick(): void {
    this.addRandomAEvent();

    for (let i = 0; i < this.aevents.length; i++) {
      this.isSelected(i);
      this.highlightRow = i;
    }
  }

  addRandomAEvent(): void {
    this.aevents.push(AEvent.createRandomAEvent());
  }
}

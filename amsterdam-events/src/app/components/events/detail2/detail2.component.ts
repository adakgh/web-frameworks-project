import {Component, Input, OnInit, Output, EventEmitter} from '@angular/core';
import {AEvent} from '../../../models/a-event';

@Component({
  selector: 'app-detail2',
  templateUrl: './detail2.component.html',
  styleUrls: ['./detail2.component.css']
})
export class Detail2Component implements OnInit {
  @Input() editedEvent: AEvent;
  aEvents: AEvent [];
  @Output() removeEvent = new EventEmitter<AEvent>();

  constructor() {
  }

  ngOnInit(): void {
  }

  deleteClick(event): void {
    const index = this.aEvents.indexOf(event, 0);

    for (let i = 0; i < this.aEvents.length; i++) {
      if (index > -1) {
        this.aEvents.splice(index, 1);
      } else {
        return;
      }
    }
  }

  removeEditedEvent() {
    this.removeEvent.emit(this.editedEvent);
  }
}

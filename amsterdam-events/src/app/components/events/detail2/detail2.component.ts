import {Component, Input, OnInit, Output, EventEmitter} from '@angular/core';
import {AEvent} from '../../../models/a-event';

@Component({
  selector: 'app-detail2',
  templateUrl: './detail2.component.html',
  styleUrls: ['./detail2.component.css']
})
export class Detail2Component implements OnInit {
  aEvents: AEvent [];
  @Input() editedEvent: AEvent;
  @Output() removeEvent = new EventEmitter<AEvent>();

  constructor() {
  }

  ngOnInit(): void {
  }
  
  removeEditedEvent() {
    this.removeEvent.emit(this.editedEvent);
  }
}

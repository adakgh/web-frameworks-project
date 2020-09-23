import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {AEvent, AEventStatus} from 'src/app/models/a-event';
import {AEventsService} from 'src/app/services/a-events.service';

@Component({
  selector: 'app-detail3',
  templateUrl: './detail3.component.html',
  styleUrls: ['./detail3.component.css']
})
export class Detail3Component implements OnInit, OnChanges {

  editedAeventId: number;
  editedAevent: AEvent;

  @Input()
  set editedAEventId(id: number) {
    this.editedAeventId = id;
    this.editedAevent = this.aEventsService.aEvents[id];
  }

  get editedAEventId() {
    return this.editedAeventId;
  }

  constructor(private aEventsService: AEventsService) {
    console.log('it\'s working');
  }

  saveClick() {
    this.aEventsService.update(this.editedAeventId, this.editedAevent);
  }

  clearClick() {
    if (this.popup()) {
      this.editedAevent.title = null;
      this.editedAevent.participationFee = null;
      this.editedAevent.end = null;
      this.editedAevent.status = null;
      this.editedAevent.start = null;
      this.editedAevent.maxParticipants = null;
    }
  }

  resetClick() {
    if (this.popup()) {
      this.editedAevent.title = this.aEventsService.aEvents[this.editedAeventId].title;
      this.editedAevent.start = this.aEventsService.aEvents[this.editedAeventId].start;
      this.editedAevent.end = this.aEventsService.aEvents[this.editedAeventId].end;
      this.editedAevent.status = this.aEventsService.aEvents[this.editedAeventId].status;
      this.editedAevent.maxParticipants = this.aEventsService.aEvents[this.editedAeventId].maxParticipants;
      this.editedAevent.participationFee = this.aEventsService.aEvents[this.editedAeventId].participationFee;
    }
  }

  cancelClick() {
    if (this.popup()) {
      this.editedAevent = null;
      this.editedAeventId = -1;
    }
  }

  popup(): boolean {
    if (this.nothingHasChanged()) {
      return true;
    } else {
      return confirm('are you sure to discard changes!');
    }
  }

  ngOnInit(): void {
  }

  setStatusType(status: string): void {
    const enumStatus: AEventStatus = AEventStatus[status];
    this.editedAevent.status = enumStatus;
  }

  getStatusTypes(): Array<string> {
    const keys: Array<string> = Object.keys(AEventStatus);
    return keys;
  }

  ngOnChanges(): void {
    this.editedAevent = Object.assign(AEventStatus, this.aEventsService.findById(this.editedAeventId));
  }

  saveChanges(): void {
  }

  nothingHasChanged(): boolean {
    return this.editedAevent.equals(
      this.aEventsService.aEvents[this.editedAeventId]);
  }
}

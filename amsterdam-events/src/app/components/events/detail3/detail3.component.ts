import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {AEvent, AEventStatus} from 'src/app/models/a-event';
import {AEventsService} from 'src/app/services/a-events.service';
import {Overview3Component} from '../overview3/overview3.component';
import {Detail2Component} from '../detail2/detail2.component';

@Component({
  selector: 'app-detail3',
  templateUrl: './detail3.component.html',
  styleUrls: ['./detail3.component.css']
})
export class Detail3Component implements OnInit {
  @Input() editedAeventId: number;
  @Input() editedAevent: AEvent;

  @Input() isEdited: boolean;
  @Input() copyEditedEvent: AEvent;
  @Input() copyEditedEventId: number;

  @Output() editedEventIdChange = new EventEmitter<number>();
  @Output() checkEditedEventChange = new EventEmitter<boolean>();

  @Input()
  set editedAEventId(id: number) {
    this.editedAeventId = id;
    this.editedAevent = this.aEventsService.getaEvents()[id];
  }

  // tslint:disable-next-line:typedef
  get editedAEventId() {
    return this.editedAeventId;
  }

  constructor(private aEventsService: AEventsService) {
    console.log('it\'s working');
  }

  // tslint:disable-next-line:typedef
  onSetTO(aEvent: AEvent) {
    this.aEventsService.update(this.editedAeventId, aEvent);
  }

  // tslint:disable-next-line:typedef
  deleteClick() {
    if (this.popup) {
      this.aEventsService.remove(this.editedAeventId);
      this.isEdited = null;
    }
  }

  // tslint:disable-next-line:typedef
  saveClick() {
    this.aEventsService.update(this.editedAeventId, this.editedAevent);
  }

  // tslint:disable-next-line:typedef
  clearClick() {
    if (this.popup()) {
      this.editedAevent.title = null;
      this.editedAevent.description = null;
      this.editedAevent.participationFee = null;
      this.editedAevent.end = null;
      this.editedAevent.status = null;
      this.editedAevent.start = null;
      this.editedAevent.maxParticipants = null;
    }
  }

  // tslint:disable-next-line:typedef
  resetClick() {
    if (this.popup()) {
      this.editedAevent.title = this.aEventsService.getaEvents()[this.editedAeventId].title;
      this.editedAevent.start = this.aEventsService.getaEvents()[this.editedAeventId].start;
      this.editedAevent.end = this.aEventsService.getaEvents()[this.editedAeventId].end;
      this.editedAevent.status = this.aEventsService.getaEvents()[this.editedAeventId].status;
      this.editedAevent.maxParticipants = this.aEventsService.getaEvents()[this.editedAeventId].maxParticipants;
      this.editedAevent.participationFee = this.aEventsService.getaEvents()[this.editedAeventId].participationFee;
    }
  }

  // tslint:disable-next-line:typedef
  cancelClick() {
    if (this.popup()) {
      this.editedAevent = null;
      this.editedAeventId = -1;
    }
  }

  // tslint:disable-next-line:typedef
  popup() {
    return confirm('are you sure to discard unsaved changes!');
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

  // tslint:disable-next-line:use-lifecycle-interface
  ngOnChanges(): void {
    this.editedAevent = Object.assign(AEventStatus, this.aEventsService.findById(this.editedAeventId));
  }

  saveChanges(): void {
  }

  nothingHasChanged(): boolean {
    return this.editedAevent.equals(
      this.aEventsService.getaEvents()[this.editedAeventId]);
  }
}

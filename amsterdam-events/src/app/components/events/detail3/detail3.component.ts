import {Component, Input, OnInit} from '@angular/core';
import {AEvent, AEventStatus} from 'src/app/models/a-event';
import {AEventsService} from 'src/app/services/a-events.service';

@Component({
  selector: 'app-detail3',
  templateUrl: './detail3.component.html',
  styleUrls: ['./detail3.component.css']
})
export class Detail3Component implements OnInit {
  @Input()
  get editedAEventId(): number {
    return this._editedAEventId;
  }

  // tslint:disable-next-line:typedef
  set editedAEventId(id: number) {
    this._editedAEventId = id;

    // tslint:disable-next-line:max-line-length
    this.editedAEvent = Object.assign({},
      this.aEventService.findById(this.editedAEventId));
  }

  // tslint:disable-next-line:variable-name
  @Input() _editedAEventId = -1;

  @Input() editedAEvent: AEvent;
  uneditedAEvent: AEvent;
  savedBeforeDelete = true;

  constructor(private aEventService: AEventsService) {
  }

  ngOnInit(): void {
    this.aEventService.update(this.editedAEventId, this.editedAEvent);
    this.resetSelectedAEvent();
  }

  // tslint:disable-next-line:typedef
  getEventStatus(): Array<string> {
    return Object.keys(AEventStatus);
  }

  // tslint:disable-next-line:typedef
  popup() {
    // tslint:disable-next-line:triple-equals no-empty
    // @ts-ignore
    return confirm('are you sure to discard unsaved changes?');
  }

  // the 5 buttons related to detail 3
  // tslint:disable-next-line:typedef
  deleteClick() {
    // first check if changes saved else do not delete
    if (this.popup()) {
      this.aEventService.deleteById(this.editedAEvent.id);
      this.editedAEventId = -1;
    }
    this.savedBeforeDelete = true;
  }

  // tslint:disable-next-line:typedef no-empty
  saveClick(): void {
    // tslint:disable-next-line:triple-equals
    this.aEventService.save(this.editedAEvent);
    this.savedBeforeDelete = false;
    this.resetSelectedAEvent();
  }

  // tslint:disable-next-line:typedef no-empty
  clearClick() {
    if (this.popup()) {
      // @ts-ignore
      const event = new AEvent();
      event.id = this.editedAEvent.id;
      this.editedAEvent = event;
    }
  }

  // tslint:disable-next-line:no-empty typedef
  resetClick() {
    if (this.popup()) {
      this.editedAEvent = Object.assign({}, this.aEventService.findById(this.editedAEventId));
      this.editedAEventId = -1;
    }
  }

  // tslint:disable-next-line:no-empty typedef
  cancelClick() {
    if (this.popup()) {
      this.editedAEvent = Object.assign({}, this.aEventService.findById(this.editedAEventId));
      this.editedAEventId = -1;
    }
  }

  // tslint:disable-next-line:typedef
  hasChanged() {
    // tslint:disable-next-line:triple-equals
    if (JSON.stringify(this.editedAEvent) == JSON.stringify(this.aEventService.findById(this.editedAEventId))) {
      return true;
    }
    this.savedBeforeDelete = true;
    return false;
  }

  // only enable delete button if changes have been saved
  // tslint:disable-next-line:typedef
  hasSaved() {
  }

  resetSelectedAEvent(): void {
    // @ts-ignore
    this.editedAEvent = Object.assign(new AEvent(), this.aEventService.findById(this.editedAEventId));
  }
}

import {Injectable} from '@angular/core';
import {AEvent, AEventStatus} from '../models/a-event';

@Injectable({
  providedIn: 'root'
})
export class AEventsService {
  // tslint:disable-next-line:variable-name
  private _aEvents: AEvent[];

  constructor() {
    this._aEvents = [];
    for (let i = 0; i < 9; i++) {
      this._aEvents.push(this.addRandomAEvent(i));
    }
  }

  add(aEvent: AEvent): number {
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < this._aEvents.length; i++) {
      this._aEvents.push(aEvent);
    }
    return this._aEvents.length;
  }

  // tslint:disable-next-line:typedef
  update(id: number, aEvent: AEvent) {
    this._aEvents[id] = aEvent;
  }

  // tslint:disable-next-line:typedef
  remove(id: number) {
    this._aEvents.splice(id, 1);
  }

  private addRandomAEvent(id: number): AEvent {
    return new AEvent(
      id,
      'The Fantastic Event-' + (id + 20001),
      new Date(2020, Math.floor(3 + Math.random() * 3), Math.random() * 31),
      new Date(2020, Math.floor(9 + Math.random() * 3), Math.random() * 31),
      'example description', AEvent.getRandomStatue(),
      Math.random() < 0.5, Math.round(((Math.random() * 50) + Number.EPSILON) * 100.0) / 100.0,
      AEvent.createRandomAEvent().maxParticipants);
  }

  public findAll(): AEvent[] {
    return this._aEvents;
  }

  public findById(id: number): AEvent {
    return this.findById(id);
  }

  public save(aEvent: AEvent): void {
    this._aEvents[aEvent.id] = aEvent;
  }

  deleteById(id: number): AEvent {
    const aEvent: AEvent = this.findById(id);
    this._aEvents.splice(id, 1);
    return aEvent;
  }


  getaEvents(): AEvent[] {
    return this._aEvents;
  }
}

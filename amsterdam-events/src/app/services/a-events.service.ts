import {Injectable} from '@angular/core';
import {AEvent, AEventStatus} from '../models/a-event';

@Injectable({
  providedIn: 'root'
})
export class AEventsService {

  public aEvents: AEvent[];

  constructor() {
    this.aEvents = [];
    for (let i = 0; i < 9; i++) {
      this.aEvents.push(this.addRandomAEvent(i));
    }
  }

  add(aEvent: AEvent): number {

    for (let i; i < this.aEvents.length; i++) {
      this.aEvents.push(aEvent);
    }
    return this.aEvents.length;
  }

  update(id: number, aEvent: AEvent) {
    this.aEvents[id] = aEvent;
  }

  remove(id: number) {
    this.aEvents.splice(id, 1);
  }

  private addRandomAEvent(id: number): AEvent {
    return new AEvent(
      id,
      'The Fantastic Event-' + id,
      AEventStatus.DRAFT,
      new Date(2020, Math.floor(3 + Math.random() * 3), Math.random() * 31),
      true,
      new Date(2020, Math.floor(9 + Math.random() * 3), Math.random() * 31),
      Math.floor(Math.random() * 100),
      'event description',
      Math.floor(Math.random() * 1000)
    );
  }

  public findAll(): AEvent[] {
    return this.aEvents;
  }

  public findById(id: number): AEvent {
    return this.findById(id);
  }

  public save(aEvent: AEvent): void {
    this.aEvents[aEvent.id] = aEvent;
  }

  deleteById(id: number): AEvent {
    const aEvent: AEvent = this.findById(id);
    this.aEvents.splice(id, 1);
    return aEvent;
  }
}

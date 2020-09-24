import {Injectable} from '@angular/core';
import {AEvent} from '../models/a-event';

@Injectable({
  providedIn: 'root'
})
export class AEventsService {
  aEvents: AEvent[];
  selectedAEvent: AEvent;

  constructor() {
    this.aEvents = [];
    for (let i = 0; i < 9; i++) {
      this.addRandomAEvent();
    }
  }

  // CRUD operations
  // retrieving the list of all a-events
  findAll(): AEvent[] {
    return this.aEvents;
  }

  // retrieving one a-event, identified by a given id
  findById(id: number): AEvent {
    // tslint:disable-next-line:triple-equals
    return this.aEvents.find((event) => event.id == id);
  }

  // saving an updated or new a-event and returning the previous instance with the same id, or null
  // if no such a-event exists yet
  save(aEvent: AEvent): void {
    // tslint:disable-next-line:triple-equals
    this.aEvents[this.aEvents.findIndex((event) => event.id == aEvent.id)] = aEvent;
  }

  // deleting the a-event identified by the given id, and returning the a-event that was deleted, or null if none existed
  deleteById(id: number): AEvent {
    const aEvent: AEvent = this.findById(id);
    this.aEvents.splice(id, 1);
    return aEvent;
  }

  // tslint:disable-next-line:typedef
  addRandomAEvent() {
    return this.aEvents.push(AEvent.createRandomAEvent());
  }

  // tslint:disable-next-line:typedef
  update(id: number, aEvent: AEvent) {
    this.aEvents[id] = aEvent;
  }

  getaEvents(): AEvent[] {
    return this.aEvents;
  }
}

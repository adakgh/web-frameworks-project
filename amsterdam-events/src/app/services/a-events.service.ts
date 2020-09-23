import {Injectable} from '@angular/core';
import {AEvent} from "../models/a-event";

@Injectable({
  providedIn: 'root'
})
export class AEventsService {

  public aEvents: AEvent[];

  constructor() {
    this.aEvents = [];
    for (let i = 0; i < 9; i++) {
      // this.addRandomAEvent()
    };
  }

  findAll(): AEvent[] {
  return this.aEvents;
  }

  findById(id: number): AEvent {
    return this.findById(id);
  }

  save(aEvent: AEvent): AEvent {
    return aEvent;
  }

  deleteById(id: number): AEvent {
    return this.deleteById(id);
  }
}

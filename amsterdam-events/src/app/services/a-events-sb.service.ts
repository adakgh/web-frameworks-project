import {Observable, Subject} from 'rxjs';
import {AEvent} from '../models/a-event';
import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';

@Injectable()
export class AEventsSbService {
  aEvents: AEvent[];
  baseUrl = 'http://localhost:8080/aevents';
  onDataLoaded: Subject<AEvent[]>;

  constructor(private http: HttpClient) {
    // calling method such that upon instantiation, it is immediately populated with data from the backend
    this.restGetAEvents();
  }

  // handles the response by caching all retrieved aevents in the local array in the service
  private restGetAEvents(): Observable<AEvent[]> {
    this.onDataLoaded = new Subject<AEvent[]>();

    this.http.get<AEvent[]>(this.baseUrl).subscribe((data) => {
        console.log(data);
        data.map(element => AEvent.trueCopy(element));
        this.aEvents = data;
        this.onDataLoaded.next(this.aEvents);
      }, (err) => console.log(err)
    );

    return this.onDataLoaded;
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

  // tslint:disable-next-line:typedef
  save(aEvent: AEvent) {
    // tslint:disable-next-line:triple-equals
    this.aEvents[this.aEvents.findIndex((idx) => idx.id == aEvent.id)] = aEvent;
    // redirecting to http put request
    this.restPutAEvent(aEvent).subscribe(data => console.log(data));
  }

  deleteById(id: number): AEvent {
    // redirecting to http delete request
    this.restDeleteAEvent(id);

    // finding the index and removing from the a-events list
    // tslint:disable-next-line:triple-equals
    const index = this.aEvents.findIndex((idx) => idx.id == id);
    // tslint:disable-next-line:triple-equals
    if (index == -1) {
      return null;
    } else {
      this.aEvents.splice(index, 1);
    }
  }

  // HTTPClient requests
  // adding a new a-event
  public restPostAEvent(aEvent): Observable<AEvent> {
    // setting the id as 0 so the id is generated automatically in the backend
    aEvent.id = 0;
    aEvent.title = 'The Fantastic Event-' + (this.aEvents.length + 20001);

    // @ts-ignore
    return this.http.post<AEvent[]>(this.baseUrl, aEvent);
  }

  // saving/updating an a-event
  public restPutAEvent(aEvent): Observable<AEvent> {
    // @ts-ignore
    return this.http.put<AEvent[]>(this.baseUrl + '/' + aEvent.id, aEvent);
  }

  // deleting an a-event
  public restDeleteAEvent(aEventId): void {
    this.http.delete<AEvent[]>(this.baseUrl + '/' + aEventId).subscribe(
      (event) => {
        console.log(event);
      }, (err) => console.log(err)
    );
  }
}

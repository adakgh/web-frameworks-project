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
      }
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

  // TODO: let method return the observable of the http-request
  // public addRandomAEvent(): void {
  //   // @ts-ignore
  //   const aEvent = new AEvent();
  //   aEvent.title = 'The Fantastic Event-' + (this.aEvents.length++ + 20001);
  //   aEvent.start = AEvent.createRandomAEvent().start;
  //   aEvent.end = AEvent.createRandomAEvent().end;
  //   aEvent.description = AEvent.createRandomAEvent().description;
  //   aEvent.status = AEvent.createRandomAEvent().status;
  //   aEvent.participationFee = AEvent.createRandomAEvent().participationFee;
  //   aEvent.isTicketed = AEvent.createRandomAEvent().isTicketed;
  //   aEvent.maxParticipants = AEvent.createRandomAEvent().maxParticipants;
  //
  //   this.aEvents.push(aEvent);
  //   this.restPostAEvent(aEvent);
  // }

  // TODO
  public restPostAEvent(aEvent): Observable<AEvent> {
    // @ts-ignore
    aEvent = new AEvent();
    aEvent.title = 'The Fantastic Event-' + (this.aEvents.length++ + 20001);
    aEvent.start = AEvent.createRandomAEvent().start;
    aEvent.end = AEvent.createRandomAEvent().end;
    aEvent.description = AEvent.createRandomAEvent().description;
    aEvent.status = AEvent.createRandomAEvent().status;
    aEvent.participationFee = AEvent.createRandomAEvent().participationFee;
    aEvent.isTicketed = AEvent.createRandomAEvent().isTicketed;
    aEvent.maxParticipants = AEvent.createRandomAEvent().maxParticipants;

    // @ts-ignore
    return this.http.post<AEvent[]>(this.baseUrl, aEvent).subscribe(
      (event) => {
        console.log(event);
      }
    );
  }

  public restPutAEvent(aEvent): Observable<AEvent> {
    // @ts-ignore
    return this.http.put<AEvent[]>(this.baseUrl + '/' + aEvent.id, aEvent).subscribe(
      (event) => {
        console.log(event);
      }
    );
  }

  public restDeleteAEvent(aEventId): void {
    // first find the index
    const aEvent: AEvent = this.findById(aEventId);
    aEventId = this.aEvents.indexOf(aEvent);
    // then delete
    this.http.delete<AEvent[]>(this.baseUrl + '/' + aEventId).subscribe(
      (event) => {
        console.log(event);
      }
    );
  }

}

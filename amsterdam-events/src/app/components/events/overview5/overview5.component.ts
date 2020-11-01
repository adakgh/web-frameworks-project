import {Component, OnInit} from '@angular/core';
import {AEvent} from '../../../models/a-event';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {AEventsSbService} from '../../../services/a-events-sb.service';

@Component({
  selector: 'app-overview5',
  templateUrl: './overview5.component.html',
  styleUrls: ['./overview5.component.css']
})
export class Overview5Component implements OnInit {
  selectedAEventId = -1;
  aEvents: AEvent[];
  selectedEvent: AEvent;

  constructor(public aEventsService: AEventsSbService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
    this.aEvents = this.aEventsService.findAll();
  }

  ngOnInit(): void {
    this.activatedRoute.firstChild.params
      .subscribe((params: Params) => {
        this.selectedAEventId = (params.id || -1);
      });
  }

  handelClick(): void {
    // @ts-ignore
    this.aEventsService.restPostAEvent(AEvent.createRandomAEvent()).subscribe( event => {
      this.aEventsService.aEvents.push(AEvent.trueCopy(event));
    });
  }

  onSelect(eId: number): void {
    // this.router.navigate([eId], {relativeTo: this.activatedRoute});

    this.router.navigate([eId], {relativeTo: this.activatedRoute});
    console.log(eId);
  }
}

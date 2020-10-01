import {Component, OnInit} from '@angular/core';
import {AEvent} from '../../../models/a-event';
import {AEventsService} from '../../../services/a-events.service';
import {ActivatedRoute, Params, Router} from '@angular/router';

@Component({
  selector: 'app-overview4',
  templateUrl: './overview4.component.html',
  styleUrls: ['./overview4.component.css']
})
export class Overview4Component implements OnInit {
  selectedAEventId = -1;
  aevents: AEvent[];
  selectedEvent: AEvent;

  constructor(public aEventsService: AEventsService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
    this.aevents = aEventsService.findAll();
  }

  onEventSelected(aEvent: AEvent): void {
    this.selectedAEventId = aEvent.id;
  }

  handelClick(): void {
    this.addRandomAEvent();
  }

  addRandomAEvent(): void {
    this.aevents.push(AEvent.createRandomAEvent());
  }

  // tslint:disable-next-line:typedef
  onSelect(eId: number) {
    // this.router.navigate([eId], {relativeTo: this.activatedRoute});

    this.router.navigate([eId], {relativeTo: this.activatedRoute});
    console.log(eId);
  }

  ngOnInit(): void {
    // this.activatedRoute.params.subscribe(
    //     (params: Params) => {
    //       this.selectedAEventId = params.id;
    //       this.selectedEvent = this.aEventsService.getaEvents()[params.id];
    //     }
    //   );

    this.activatedRoute.firstChild.params
      .subscribe((params: Params) => {
        this.selectedAEventId = (params.id || -1);
      });
  }
}

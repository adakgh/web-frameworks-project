import {Component, OnInit} from '@angular/core';
import {AEvent} from '../../../models/a-event';
import {AEventsService} from '../../../services/a-events.service';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-overview4',
  templateUrl: './overview4.component.html',
  styleUrls: ['./overview4.component.css']
})
export class Overview4Component implements OnInit {
  selectedAEventId = -1;
  aevents: AEvent[];

  constructor(public aEventsService: AEventsService, private router: Router,
              private activatedRoute: ActivatedRoute) {
    this.aevents = aEventsService.findAll();
  }

  onEventSelected(aEvent: AEvent): void {
    this.selectedAEventId = aEvent.id;
  }

  ngOnInit(): void {
  }

  handelClick(): void {
    this.addRandomAEvent();
  }

  addRandomAEvent(): void {
    this.aevents.push(AEvent.createRandomAEvent());
  }

  // tslint:disable-next-line:typedef
  onSelect(eId: number) {
    this.router.navigate([eId], {relativeTo: this.activatedRoute});
  }
}

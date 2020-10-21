import { Component, OnInit } from '@angular/core';
import {AEvent} from '../../../models/a-event';
import {AEventsService} from '../../../services/a-events.service';
import {ActivatedRoute, Params, Router} from '@angular/router';

@Component({
  selector: 'app-overview5',
  templateUrl: './overview5.component.html',
  styleUrls: ['./overview5.component.css']
})
export class Overview5Component implements OnInit {
  selectedAEventId = -1;
  aevents: AEvent[];

  constructor(public aEventsService: AEventsService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
    this.aevents = aEventsService.findAll();
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
    this.activatedRoute.firstChild.params
      .subscribe((params: Params) => {
        this.selectedAEventId = (params.id || -1);
      });
  }
}

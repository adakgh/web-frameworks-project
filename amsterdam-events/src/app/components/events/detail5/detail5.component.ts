import {Component, OnDestroy, OnInit} from '@angular/core';
import {AEvent, AEventStatus} from '../../../models/a-event';
import {AEventsService} from '../../../services/a-events.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {Subscription} from 'rxjs';

@Component({
  selector: 'app-detail5',
  templateUrl: './detail5.component.html',
  styleUrls: ['./detail5.component.css']
})
export class Detail5Component implements OnInit, OnDestroy {
  // tslint:disable-next-line:variable-name
  _editedAEventId = -1;
  editedAEvent: AEvent;
  uneditedAEvent: AEvent;
  savedBeforeDelete = true;

  get editedAEventId(): number {
    return this._editedAEventId;
  }

  set editedAEventId(id: number) {
    this._editedAEventId = id;

    // tslint:disable-next-line:max-line-length
    this.editedAEvent = Object.assign({}, this.aEventService.findById(this.editedAEventId));
  }

  constructor(private aEventService: AEventsService, private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  childParamSubscription: Subscription = null;

  ngOnInit(): void {
    this.childParamSubscription =
      this.activatedRoute.params
        .subscribe((params: Params) => {
            console.log('detail setup id=' + params.id);
            // retrieve the event to be edited from the server
            this.editedAEventId = (params.id || -1);
          }
        );
  }

  // tslint:disable-next-line:use-lifecycle-interface
  ngOnDestroy(): void {
    // tslint:disable-next-line:no-unused-expression
    this.childParamSubscription &&
    this.childParamSubscription.unsubscribe();
  }

  // tslint:disable-next-line:typedef
  getEventStatus(): Array<string> {
    return Object.keys(AEventStatus);
  }

  // tslint:disable-next-line:typedef
  popup() {
    return confirm('are you sure to discard unsaved changes?');
  }

  // the 5 buttons related to detail 3
  // tslint:disable-next-line:typedef
  deleteClick() {
    // first check if changes saved else do not delete
    if (this.popup()) {
      this.aEventService.deleteById(this.editedAEvent.id);
      this.router.navigate(['..'], {relativeTo: this.activatedRoute});
    }
    this.savedBeforeDelete = true;
  }

  // tslint:disable-next-line:typedef no-empty
  // saveClick(onclick: any): void {
  //   // tslint:disable-next-line:triple-equals
  //   this.aEventService.save(this.editedAEvent);
  //   this.resetSelectedAEvent();
  // }

  // tslint:disable-next-line:typedef no-empty
  saveClick(): void {
    // tslint:disable-next-line:triple-equals
    this.aEventService.save(this.editedAEvent);
    this.savedBeforeDelete = false;
    this.resetSelectedAEvent();
  }

  // tslint:disable-next-line:typedef no-empty
  clearClick() {
    if (this.popup()) {
      // @ts-ignore
      const event = new AEvent();
      event.id = this.editedAEvent.id;
      this.editedAEvent = event;
    }
  }

  // tslint:disable-next-line:no-empty typedef
  resetClick() {
    if (this.popup()) {
      this.editedAEvent = Object.assign({}, this.aEventService.findById(this.editedAEventId));
      this.router.navigate(['..'], {relativeTo: this.activatedRoute});
    }
  }

  // tslint:disable-next-line:no-empty typedef
  cancelClick() {
    if (this.popup()) {
      this.editedAEvent = Object.assign({}, this.aEventService.findById(this.editedAEventId));
      this.router.navigate(['..'], {relativeTo: this.activatedRoute});
    }
  }

  // tslint:disable-next-line:typedef
  hasChanged() {
    // tslint:disable-next-line:triple-equals
    // if (JSON.stringify(this.editedAEvent) == JSON.stringify(this.aEventService.findById(this.editedAEventId))) {
    //   return true;
    // } else {
    //   return confirm('Wait! Are you sure to discard changes!');
    // }

    // tslint:disable-next-line:triple-equals
    if (JSON.stringify(this.editedAEvent) == JSON.stringify(this.aEventService.findById(this.editedAEventId))) {
      return true;
    }
    this.savedBeforeDelete = true;
    return false;
  }

// tslint:disable-next-line:typedef
//   hasSaved(): boolean {
  // @ts-ignore
  // if (this.hasChanged() === this.saveClick(onclick())) {
  //   return this.deleteClick();
  // } else {
  //   return confirm('You have to save first!');
  // }

  //   if (JSON.stringify(this.uneditedAEvent) !== JSON.stringify(this.editedAEvent)) {
  //     if (JSON.stringify(this.editedAEvent) === JSON.stringify(this.aEventService.findById(this.editedAEvent.id))) {
  //       return false;
  //     }
  //     return true;
  //   } else {
  //     return false;
  //   }
  // }

  resetSelectedAEvent(): void {
    // @ts-ignore
    this.editedAEvent = Object.assign(new AEvent(), this.aEventService.findById(this.editedAEventId));
  }
}

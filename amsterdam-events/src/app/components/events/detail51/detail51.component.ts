import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {AEvent, AEventStatus} from '../../../models/a-event';
import {Subscription} from 'rxjs';
import {AEventsSbService} from '../../../services/a-events-sb.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-detail51',
  templateUrl: './detail51.component.html',
  styleUrls: ['./detail51.component.css']
})
export class Detail51Component implements OnInit, OnDestroy {
  @ViewChild('editForm', {static: false})
  detailForm: NgForm;

  // tslint:disable-next-line:variable-name
  _editedAEventId = -1;
  editedAEvent: AEvent;
  childParamSubscription: Subscription = null;

  constructor(private aEventService: AEventsSbService, private router: Router,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    setTimeout(() => {
      this.childParamSubscription =
          this.activatedRoute.params
              .subscribe((params: Params) => {
                    console.log('detail setup id=' + params.id);
                    // retrieve the event to be edited from the server
                    this.editedAEventId = (params.id || -1);
                  }
              );
    }, 500);
  }

  ngOnDestroy(): void {
    // tslint:disable-next-line:no-unused-expression
    this.childParamSubscription && this.childParamSubscription.unsubscribe();
  }

  get editedAEventId(): number {
    return this._editedAEventId;
  }

  set editedAEventId(id: number) {
    this._editedAEventId = id;

    // tslint:disable-next-line:max-line-length
    this.editedAEvent = Object.assign({}, this.aEventService.findById(this.editedAEventId));
  }

  getEventStatus(): Array<string> {
    return Object.keys(AEventStatus);
  }

  // tslint:disable-next-line:typedef
  popup() {
    return confirm('are you sure to discard?');
  }

  // the 5 buttons related to detail 3
  deleteClick(): void {
    if (this.popup()) {
      this.aEventService.deleteById(this.editedAEvent.id);
      this.router.navigate(['..'], {relativeTo: this.activatedRoute});
      this.detailForm.form.markAsPristine();
    }
    return null;
  }

  // tslint:disable-next-line:typedef no-empty
  saveClick() {
    this.aEventService.save(this.editedAEvent);
    this.resetSelectedAEvent();
    this.detailForm.form.markAsPristine();
  }

  clearClick(): void {
    if (this.popup()) {
      // @ts-ignore
      const event = new AEvent();
      event.id = this.editedAEvent.id;
      this.editedAEvent = event;
      this.detailForm.form.markAsPristine();
    }
    return null;
  }

  resetClick(): void {
    if (this.popup()) {
      this.editedAEvent = Object.assign({}, this.aEventService.findById(this.editedAEventId));
      this.router.navigate(['..'], {relativeTo: this.activatedRoute});
      this.detailForm.form.markAsPristine();
    }
    return null;
  }

  cancelClick(): void {
    if (this.popup()) {
      this.editedAEvent = Object.assign({}, this.aEventService.findById(this.editedAEventId));
      this.router.navigate(['..'], {relativeTo: this.activatedRoute});
    }
    return null;
  }

  // tslint:disable-next-line:typedef
  hasChanged() {
    // tslint:disable-next-line:triple-equals
    if (JSON.stringify(this.editedAEvent) == JSON.stringify(this.aEventService.findById(this.editedAEventId))) {
      return true;
    }
    return false;
  }

  resetSelectedAEvent(): void {
    this.editedAEvent = Object.assign({}, this.aEventService.findById(this.editedAEventId));
  }
}


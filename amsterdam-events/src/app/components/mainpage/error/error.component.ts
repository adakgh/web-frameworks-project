import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.css']
})
export class ErrorComponent implements OnInit {
  public route;

  constructor(private currentRoute: ActivatedRoute) {
    this.route = currentRoute.url;
  }

  ngOnInit(): void {
  }
}

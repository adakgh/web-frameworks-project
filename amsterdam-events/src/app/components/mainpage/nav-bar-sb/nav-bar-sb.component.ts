import {Component, OnInit} from '@angular/core';
import {SessionSbService} from '../../../services/session-sb.service';

@Component({
  selector: 'app-nav-bar-sb',
  templateUrl: './nav-bar-sb.component.html',
  styleUrls: ['./nav-bar-sb.component.css']
})
export class NavBarSbComponent implements OnInit {

  constructor(public sessionService: SessionSbService) {
  }

  ngOnInit(): void {
  }

  logout(): void {
    this.sessionService.signOff();
  }
}

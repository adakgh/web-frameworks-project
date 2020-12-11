import {Component, OnInit} from '@angular/core';
import {SessionSbService} from '../../../services/session-sb.service';

@Component({
  selector: 'app-header-sb',
  templateUrl: './header-sb.component.html',
  styleUrls: ['./header-sb.component.css']
})
export class HeaderSbComponent implements OnInit {

  time = Date.now();

  constructor(public sessionService: SessionSbService) {
  }

  ngOnInit(): void {
  }
}

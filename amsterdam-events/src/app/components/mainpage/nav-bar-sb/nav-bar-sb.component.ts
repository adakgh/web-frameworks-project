import {Component, OnInit} from '@angular/core';
import {SessionSbService} from '../../../services/session-sb.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-nav-bar-sb',
  templateUrl: './nav-bar-sb.component.html',
  styleUrls: ['./nav-bar-sb.component.css']
})
export class NavBarSbComponent implements OnInit {

  constructor(public sessionService: SessionSbService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  logout(): void {
    this.sessionService.signOff();
    this.router.navigate(['']).then(() => {
      window.location.reload();
    });
  }
}

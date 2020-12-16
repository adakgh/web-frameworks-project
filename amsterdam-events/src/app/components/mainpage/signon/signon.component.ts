import {Component, OnInit} from '@angular/core';
import {SessionSbService} from '../../../services/session-sb.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {User} from '../../../models/user';
import {Router} from '@angular/router';

@Component({
  selector: 'app-signon',
  templateUrl: './signon.component.html',
  styleUrls: ['./signon.component.css']
})
export class SignonComponent implements OnInit {
  loginForm: FormGroup;
  loginData: User;

  constructor(public sessionService: SessionSbService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      email: new FormControl('', Validators.required),
      password: new FormControl('', Validators.required)
    });

  }

  login(): void {
    this.form2object();
    this.sessionService.signOn(this.loginData.email, this.loginData.password).subscribe(
      data => {
        this.router.navigate(['']).then(() => {
          window.location.reload();
        });
      }, (error) => {
        alert('Emailaddress and/or password are incorrect! Please try again');
        console.log(error);
      });

  }

  form2object(): void {
    this.loginData = Object.assign(new User(), this.loginForm.value);
  }

}

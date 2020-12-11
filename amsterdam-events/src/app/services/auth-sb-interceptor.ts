import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SessionSbService} from './session-sb.service';

@Injectable()
export class AuthSbInterceptor implements HttpInterceptor {


  constructor(private session: SessionSbService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.session.getTokenFromSessionStorage();

    if (token != null) {
      // TODO clone te request, adding the token in the authorization header
      // pass on the cloned request to the next handler

    } else {
      // just forward the request to the next handler
      return next.handle(req);
    }
  }

}

import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SessionSbService} from './session-sb.service';
import {share} from 'rxjs/operators';

@Injectable()
export class AuthSbInterceptor implements HttpInterceptor {


  constructor(private session: SessionSbService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.session.getTokenFromSessionStorage();

    if (token != null) {
      // TODO clone te request, adding the token in the authorization header
      // pass on the cloned request to the next handler

      const headersConfig = {
        Authorization: 'Bearer ' + token
      };

      const cloned = req.clone({
        setHeaders: headersConfig
      });

      // using pipe(share()) to prevent multiple submissions per subscriber (observables are cold)
      const observable = next.handle(cloned).pipe(share());

      observable.subscribe((data) => {
        // For future usage: if you want to intercept responses, this is the place :-)
        console.log('intercepting response ', data);
      }, (error) => {
        console.error('error in interceptor:', error);
      });

      return observable;

    } else {
      // just forward the request to the next handler
      return next.handle(req);
    }
  }
}

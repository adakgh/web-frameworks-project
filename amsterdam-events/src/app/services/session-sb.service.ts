import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../models/user';
import {shareReplay} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SessionSbService {
  public readonly BACKEND_AUTH_URL = 'http://localhost:8080/authenticate';

  public currentUserName: string = null;

  constructor(private http: HttpClient) {
    this.getTokenFromSessionStorage();
  }

  // logs on to the backend, and retrieves user details and the JWT authentication token
  // from the backend
  signOn(email: string, password: string): Observable<any> {
    console.log('signon ' + email + '/' + password);
    const signInReponse =
      this.http.post<HttpResponse<User>>(this.BACKEND_AUTH_URL + '/login',
        {eMail: email, passWord: password},
        // observe gives access to the complete response of the request
        {observe: 'response'}).pipe(shareReplay(1));

    signInReponse.subscribe(response => {
        console.log(response);
        this.saveTokenIntoSessionStorage(
          // extract the authentication token from the Authorization header
          response.headers.get('Authorization'),
          // extract the user name from the response body
          (response.body as unknown as User).name);
      },
      error => {
        console.log(error);
        this.saveTokenIntoSessionStorage(null, null);
      }
    );

    return signInReponse;
  }

  // discards user details and the JWT authentication token from the session (and local
  // browser storage)
  signOff(): void {
    sessionStorage.removeItem('token');
  }

  // indicates whether a user has been logged on into the session or not.
  isAuthenticated(): boolean {
    return this.currentUserName != null;
  }

  // retrieves the JWT authentication token and user details from the session (or local
  // browser storage).
  getTokenFromSessionStorage(): string {
    return sessionStorage.getItem('token');
  }

  // saves the JWT authentication token and user details into the session (and local
  // browser storage).
  saveTokenIntoSessionStorage(token: string, username: string): void {
    token = token.replace('Bearer ', '');
    sessionStorage.setItem('token', token);

    this.currentUserName = username;
  }
}
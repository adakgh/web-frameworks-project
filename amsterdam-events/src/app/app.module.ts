import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';

import {AEventsSbService} from './services/a-events-sb.service';
import {SessionSbService} from './services/session-sb.service';
import {AuthSbInterceptor} from './services/auth-sb-interceptor';

import {HeaderComponent} from './components/mainpage/header/header.component';
import {HomeComponent} from './components/mainpage/home/home.component';
import {NavBarComponent} from './components/mainpage/nav-bar/nav-bar.component';

import {Overview1Component} from './components/events/overview1/overview1.component';
import {Overview2Component} from './components/events/overview2/overview2.component';
import {Overview3Component} from './components/events/overview3/overview3.component';
import {Detail2Component} from './components/events/detail2/detail2.component';
import {Detail3Component} from './components/events/detail3/detail3.component';
import {ErrorComponent} from './components/mainpage/error/error.component';
import {Overview4Component} from './components/events/overview4/overview4.component';
import {Detail4Component} from './components/events/detail4/detail4.component';
import {Detail5Component} from './components/events/detail5/detail5.component';
import {Overview5Component} from './components/events/overview5/overview5.component';
import {HeaderSbComponent} from './components/mainpage/header-sb/header-sb.component';
import {SignonComponent} from './components/mainpage/signon/signon.component';
import {NavBarSbComponent} from './components/mainpage/nav-bar-sb/nav-bar-sb.component';
import { Detail51Component } from './components/events/detail51/detail51.component';

// const appRoutes: Routes = [
//   {path: 'home', component: HomeComponent},
//   {path: 'events/overview1', component: Overview1Component},
//   {path: 'events/overview2', component: Overview2Component},
//   {path: 'events/overview3', component: Overview3Component},
//   {path: 'events/detail2', component: Detail2Component},
//   {path: 'events/detail3', component: Detail3Component}
// ];


const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'login', component: SignonComponent},
  {
    path: 'events', children: [
      {path: 'overview1', component: Overview1Component},
      {
        path: 'overview2', component: Overview2Component, children: [
          {path: ':id', component: Detail2Component}
        ]
      },
      {
        path: 'overview3', component: Overview3Component, children: [
          {path: ':id', component: Detail3Component}
        ]
      },
      {
        path: 'overview4', component: Overview4Component, children: [
          {path: '', pathMatch: 'full', redirectTo: '/events/overview4/-1'},
          {path: ':id', component: Detail4Component}
        ]
      },
      {
        path: 'overview5', component: Overview5Component, children: [
          {path: '', pathMatch: 'full', redirectTo: '/events/overview5/-1'},
          {path: ':id', component: Detail5Component}
        ]
      },
      {
        path: 'overview51', component: Overview5Component, children: [
          {path: '', pathMatch: 'full', redirectTo: '/events/overview51/-1'},
          {path: ':id', component: Detail51Component}
        ]
      }
    ]
  },
  {path: '**', component: ErrorComponent}
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    NavBarComponent,
    Overview1Component,
    Overview2Component,
    Overview3Component,
    Detail2Component,
    Detail3Component,
    ErrorComponent,
    Overview4Component,
    Detail4Component,
    Detail5Component,
    Overview5Component,
    HeaderSbComponent,
    SignonComponent,
    NavBarSbComponent,
    Detail51Component
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes, {useHash: true}),
    ReactiveFormsModule
  ],
  providers: [
    AEventsSbService,
    SessionSbService,
    {provide: HTTP_INTERCEPTORS, useClass: AuthSbInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})

export class AppModule {
}

import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';

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
          {path: ':id', component: Detail4Component}
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
    Detail4Component
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes, {useHash: true})
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule {
}

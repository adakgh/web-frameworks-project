import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HeaderComponent} from './components/mainpage/header/header.component';
import {HomeComponent} from './components/mainpage/home/home.component';
import {NavBarComponent} from './components/mainpage/nav-bar/nav-bar.component';
import {Overview2Component} from './components/events/overview2/overview2.component';
import {Detail2Component} from './components/events/detail2/detail2.component';
import {Overview1Component} from './components/events/overview1/overview1.component';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {HttpClientModule} from "@angular/common/http";

const appRoutes: Routes = [
  {path: 'Home', component: HomeComponent},
  {path: 'events/overview1', component: Overview1Component},
  {path: 'events/overview2', component: Overview2Component},
];

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    NavBarComponent,
    Overview2Component,
    Detail2Component,
    Overview1Component,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AgmCoreModule} from '@agm/core';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {RouterModule, Routes} from '@angular/router';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {AppComponent} from './app.component';
import {NavbarComponent} from './custom_components/navbar/navbar.component';
import { SignupComponent } from './views/signup/signup.component';
import {UserService} from './services/user/user.service';
import { LoginComponent } from './views/login/login.component';
import { NewOfferComponent } from './views/new-offer/new-offer.component';
import { StatisticsComponent } from './views/statistics/statistics.component';
import {OfferService} from "./services/offer/offer.service";
import { ReceiveOfferComponent } from './views/receive-offer/receive-offer.component';
import {Globals} from "./logged_user/logged_user";


const appRoutes: Routes = [
    {
        path: 'login',
        component: LoginComponent,
    },
    {
        path: 'signup',
        component: SignupComponent,
    },
    {
        path: 'new-offer',
        component: NewOfferComponent,
    },
    {
        path: 'receive-offer',
        component: ReceiveOfferComponent,
    },
    {
        path: 'statistics',
        component: StatisticsComponent,
    },
    {
        path: '',
        redirectTo: '/login',
        pathMatch: 'full'
    }
];

@NgModule({
    declarations: [
        AppComponent,
        NavbarComponent,
        SignupComponent,
        LoginComponent,
        NewOfferComponent,
        StatisticsComponent,
        ReceiveOfferComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        HttpClientModule,
        RouterModule.forRoot(
            appRoutes,
            {enableTracing: true} // <-- debugging purposes only
        ),
        NgbModule.forRoot()
    ],

    providers: [UserService,OfferService,Globals],
    bootstrap: [AppComponent]
})
export class AppModule {
}

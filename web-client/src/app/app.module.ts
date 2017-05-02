import "hammerjs";
import {BrowserModule} from "@angular/platform-browser";
import {NgModule} from "@angular/core";
import {ReactiveFormsModule} from "@angular/forms";
import {HttpModule, Http, XHRBackend, ConnectionBackend} from "@angular/http";
import {RootComponent} from "./components/root/root.component";
import {SignInComponent} from "./components/sign-in/sign-in.component";
import {RoutingModule} from "./routing-module";
import {MaterialModule} from "@angular/material";
import {UserService} from "./services/user/user.service";
import {PureHttpService} from "./services/pure-http/pure-http.service";
import {SecureHttpService} from "./services/secure-http/secure-http.service";
import {ErrorHandleService} from "./services/error-handle/error-handle.service";
import {SecurityService} from "./services/security/security.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MomentModule} from "angular2-moment";
import {DashboardComponent} from "./components/dashboard/dashboard.component";
import {ResumeDao} from "./dao/resume/resume.dao";
import {HeaderComponent} from "./components/header/header.component";
import {IfAuthenticatedDirective} from "./directives/if-authenticated/if-authenticated.directive";
import {ToasterModule} from "angular2-toaster";
import {ToastService} from "./services/toast/toast.service";
import {I18nModule} from "./modules/i18n/i18n.module";
import { UserSettingsComponent } from './components/user-settings/user-settings.component';

@NgModule({
  declarations: [
    RootComponent,
    SignInComponent,
    DashboardComponent,
    HeaderComponent,
    IfAuthenticatedDirective,
    UserSettingsComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    HttpModule,
    MaterialModule,
    ToasterModule,
    RoutingModule,
    MomentModule,
    I18nModule,
  ],
  providers: [
    ToastService,
    SecurityService,
    UserService,
    ErrorHandleService,
    {provide: ConnectionBackend, useClass: XHRBackend},
    PureHttpService,
    {provide: Http, useClass: SecureHttpService},
    ResumeDao
  ],
  bootstrap: [RootComponent]
})
export class AppModule {
}

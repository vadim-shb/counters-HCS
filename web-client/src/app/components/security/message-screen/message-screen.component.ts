import {Component, OnInit} from "@angular/core";
import {I18nService} from "../../../modules/i18n/i18n.service";
import {Translation} from "../../../modules/i18n/translations/translation";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'message-screen',
  templateUrl: './message-screen.component.html',
  styleUrls: ['./message-screen.component.less']
})
export class MessageScreenComponent implements OnInit {

  private i18n: Translation;
  private messageWindow: {
    title: string;
    message: string;
    linkPath: string;
    linkText: string;
  };

  constructor(private i18nService: I18nService,
              private route: ActivatedRoute,) {
    i18nService.getCurrentTranslation()
      .subscribe(translation => {
        this.i18n = translation;
      });
  }

  ngOnInit() {
    this.route.params
      .map(params => params['messageScreenType'])
      .subscribe(messageScreenType => {
        this.messageWindow = {
          title: '',
          message: '',
          linkPath: '/',
          linkText: this.i18n.security.TO_THE_MAIN_PAGE,
        };
        switch (messageScreenType) {
          case 'password-recovery-email-sent':
            this.messageWindow.title = this.i18n.security.PASSWORD_RECOVERY_EMAIL_SENT_TITLE;
            this.messageWindow.message = this.i18n.security.PASSWORD_RECOVERY_EMAIL_SENT_MESSAGE;
            break;
          case 'password-recovery-success':
            this.messageWindow.title = this.i18n.security.PASSWORD_RECOVERY_SUCCESS_TITLE;
            this.messageWindow.message = this.i18n.security.PASSWORD_RECOVERY_SUCCESS_MESSAGE;
            this.messageWindow.linkPath = '/security/sign-in';
            this.messageWindow.linkText = this.i18n.security.SIGN_IN;
            break;
          case 'password-recovery-error':
            this.messageWindow.title = this.i18n.security.PASSWORD_RECOVERY_ERROR_TITLE;
            this.messageWindow.message = this.i18n.security.PASSWORD_RECOVERY_ERROR_MESSAGE;
            break;
          case 'confirmation-email-sent':
            this.messageWindow.title = this.i18n.security.CONFIRMATION_EMAIL_SENT_TITLE;
            this.messageWindow.message = this.i18n.security.CONFIRMATION_EMAIL_SENT_MESSAGE;
            break;
          case 'email-confirmation-success':
            this.messageWindow.title = this.i18n.security.EMAIL_CONFIRMATION_SUCCESS_TITLE;
            this.messageWindow.message = this.i18n.security.EMAIL_CONFIRMATION_SUCCESS_MESSAGE;
            this.messageWindow.linkPath = '/security/sign-in';
            this.messageWindow.linkText = this.i18n.security.SIGN_IN;
            break;
        }
      });
  }

}

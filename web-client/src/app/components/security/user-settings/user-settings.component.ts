import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../../services/user/user.service";
import {I18nService} from "../../../modules/i18n/i18n.service";
import {Translation} from "../../../modules/i18n/translations/translation";
import {Http} from "@angular/http";
import {User} from "../../../domain/user";

@Component({
  selector: 'user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.less']
})
export class UserSettingsComponent implements OnInit {

  private i18n: Translation;
  private userInfoForm: FormGroup;
  private originUserInfo: User;

  get userNameFormControl(): FormControl {
    return this.userInfoForm.get('name') as FormControl;
  };

  get userLanguageFormControl(): FormControl {
    return this.userInfoForm.get('language') as FormControl;
  };

  constructor(private fb: FormBuilder,
              private userService: UserService,
              private i18nService: I18nService,
              private http: Http,
  ) {
    i18nService.getCurrentTranslation()
      .subscribe(translation => {
        this.i18n = translation;
      });

    this.userInfoForm = fb.group({
      name: ['', [Validators.required, Validators.maxLength(100)]],
      language: [''],
    });

    userService.getUser()
      .subscribe(user => {
        if (user) {
          this.originUserInfo = user;
          this.userInfoForm.setValue({
            name: user.name,
            language: user.language,
          });
        }
      });
  }

  ngOnInit() {
  }

  isOriginUserInfoChanged() {
    return this.originUserInfo.name != this.userNameFormControl.value ||
           this.originUserInfo.language != this.userLanguageFormControl.value;
  }

  saveUserInfo() {
    if (this.userInfoForm.invalid) {
      this.userNameFormControl.markAsTouched();
      return;
    }
    let userInfoChangeRequest = {
      name: this.userNameFormControl.value,
      language: this.userLanguageFormControl.value,
    };

    this.http.post(`/api/security/current-user`, userInfoChangeRequest)
      .map(response => response.json() as User)
      .subscribe(
        (user) => {
          this.userService.setUser(user);
        });
  }
}
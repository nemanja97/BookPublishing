import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import CamundaFormItem from 'src/app/model/CamundaFormItem';

@Injectable({
  providedIn: 'root',
})
export class FormsControlService {
  constructor() {}

  toFormGroup(items: CamundaFormItem[]) {
    const group: any = {};

    items.forEach((item) => {
      let validators = [];

      if (
        item.validationConstraints.length > 0 &&
        item.validationConstraints[0].name === 'required'
      ) {
        validators.push(Validators.required);
      }

      if (item.properties.type === 'email') {
        validators.push(Validators.email);
      }

      if (item.properties.type === 'password') {
        validators.push(
          Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*d)[a-zA-Zd]{8,}$')
        );
      }

      if (item.properties.min_files != undefined) {
        validators.push(
          Validators.minLength(Number.parseInt(item.properties.min_files))
        );
      }

      if (item.properties.type === 'file')
        group[item.id] = new FormControl(item.value || '', validators);
      else group[item.id] = new FormControl(item.value.value || '', validators);
    });
    return new FormGroup(group);
  }
}

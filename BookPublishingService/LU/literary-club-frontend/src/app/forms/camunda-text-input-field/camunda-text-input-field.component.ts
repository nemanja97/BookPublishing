import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

import CamundaFormItem from 'src/app/model/CamundaFormItem';

@Component({
  selector: 'app-camunda-text-input-field',
  templateUrl: './camunda-text-input-field.component.html',
  styleUrls: ['./camunda-text-input-field.component.scss'],
})
export class CamundaTextInputFieldComponent {
  @Input() field!: CamundaFormItem;
  @Input() form!: FormGroup;
  get isValid() {
    return {
      isValid: this.form.controls[this.field.id].valid,
      error: this.form.controls[this.field.id].errors,
    };
  }
}

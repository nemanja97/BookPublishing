import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

import CamundaFormItem from 'src/app/model/CamundaFormItem';

@Component({
  selector: 'app-camunda-boolean-input-field',
  templateUrl: './camunda-boolean-input-field.component.html',
  styleUrls: ['./camunda-boolean-input-field.component.scss']
})
export class CamundaBooleanInputFieldComponent {
  @Input() field!: CamundaFormItem;
  @Input() form!: FormGroup;
  get isValid() {
    return {
      isValid: this.form.controls[this.field.id].valid,
      error: this.form.controls[this.field.id].errors,
    };
  }

}

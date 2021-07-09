import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

import CamundaFormItem from 'src/app/model/CamundaFormItem';

@Component({
  selector: 'app-camunda-multi-select-input-field',
  templateUrl: './camunda-multi-select-input-field.component.html',
  styleUrls: ['./camunda-multi-select-input-field.component.scss']
})
export class CamundaMultiSelectInputFieldComponent {
  @Input() field!: CamundaFormItem;
  @Input() form!: FormGroup;
  get isValid() {
    return {
      isValid: this.form.controls[this.field.id].valid,
      error: this.form.controls[this.field.id].errors,
    };
  }
}

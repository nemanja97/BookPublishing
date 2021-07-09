import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';
import CamundaFormItem from 'src/app/model/CamundaFormItem';

@Component({
  selector: 'app-camunda-select-input',
  templateUrl: './camunda-select-input.component.html',
  styleUrls: ['./camunda-select-input.component.scss']
})
export class CamundaSelectInputComponent {
  @Input() field!: CamundaFormItem;
  @Input() form!: FormGroup;
  get isValid() {
    return {
      isValid: this.form.controls[this.field.id].valid,
      error: this.form.controls[this.field.id].errors,
    };
  }
}

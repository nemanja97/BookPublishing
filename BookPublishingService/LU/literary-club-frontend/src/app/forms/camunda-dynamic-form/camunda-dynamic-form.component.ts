import { Component, Input, OnInit, OnChanges } from '@angular/core';
import { Output, EventEmitter } from '@angular/core';
import { Form, FormGroup } from '@angular/forms';
import CamundaFormItem from 'src/app/model/CamundaFormItem';
import { FormsControlService } from 'src/app/services/forms-control.service';
import { FormsService } from 'src/app/services/forms.service';

@Component({
  selector: 'app-camunda-dynamic-form',
  templateUrl: './camunda-dynamic-form.component.html',
  styleUrls: ['./camunda-dynamic-form.component.scss']
})
export class CamundaDynamicFormComponent implements OnInit, OnChanges {

  @Input() formFields: CamundaFormItem[] = [];
  @Input() loadingData: boolean = true;

  form!: FormGroup;

  @Output() submitEvent = new EventEmitter<any>();

  constructor(
    private formsService: FormsService,
    private formsControlService: FormsControlService
  ) {
  }


  ngOnInit(): void {
    if (this.formFields !== null) {
      this.form = this.formsControlService.toFormGroup(this.formFields);
    }
  }

  ngOnChanges(): void {
    this.form = this.formsControlService.toFormGroup(this.formFields);
  }


  onSubmit() {
    this.submitEvent.emit(this.form.getRawValue())
  }

}

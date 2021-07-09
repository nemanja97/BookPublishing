import { ElementRef, HostListener } from '@angular/core';
import { Component, Input } from '@angular/core';
import { ControlValueAccessor, FormGroup } from '@angular/forms';
import CamundaFormItem from 'src/app/model/CamundaFormItem';

@Component({
  selector: 'app-camunda-file-upload',
  templateUrl: './camunda-file-upload.component.html',
  styleUrls: ['./camunda-file-upload.component.scss'],
})
export class CamundaFileUploadComponent implements ControlValueAccessor {
  @Input() field!: CamundaFormItem;
  @Input() form!: FormGroup;

  onChange: Function;
  public files: any;

  get isValid() {
    return {
      isValid: this.form.controls[this.field.id].valid,
      error: this.form.controls[this.field.id].errors,
    };
  }

  @HostListener('change', ['$event.target.files']) emitFiles( event: FileList ) {
    let files = []
    for (let i = 0; i < event.length; i++) {
      files.push(event.item(i));
    }
    this.files = files;
    this.form.patchValue({
      [this.field.id]: this.files || ""
    })
  }

  constructor( private host: ElementRef<HTMLInputElement> ) {
  }

  writeValue( value: null ) {
    this.host.nativeElement.value = '';
    this.files = null;
  }

  registerOnChange( fn: Function ) {
    this.onChange = fn;
  }

  registerOnTouched( fn: Function ) {
  }
}

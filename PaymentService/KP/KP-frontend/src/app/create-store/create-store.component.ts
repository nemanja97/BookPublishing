import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {StoreRegistration} from '../model/StoreRegistration';
import {StoreService} from '../service/store.service';

@Component({
  selector: 'app-create-store',
  templateUrl: './create-store.component.html',
  styleUrls: ['./create-store.component.scss']
})
export class CreateStoreComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private router: Router, private storeService: StoreService) {
  }

  storeForm: FormGroup;

  ngOnInit(): void {
    this.storeForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      parentStoreId: ['', []],
      storeTransactionSuccessWebhook: ['', []],
      storeTransactionFailureWebhook: ['', []],
      storeTransactionErrorWebhook: ['', []]
    });
  }

  get name() {
    return this.storeForm.controls.name.value as string;
  }

  get parentStoreId() {
    return this.storeForm.controls.parentStoreId.value as string;
  }

  get storeTransactionSuccessWebhook() {
    return this.storeForm.controls.storeTransactionSuccessWebhook.value as string;
  }

  get storeTransactionFailureWebhook() {
    return this.storeForm.controls.storeTransactionFailureWebhook.value as string;
  }
  get storeTransactionErrorWebhook() {
    return this.storeForm.controls.storeTransactionErrorWebhook.value as string;
  }

  onStoreSubmit() {
    const store = new StoreRegistration(this.name,
      this.parentStoreId,
      this.storeTransactionSuccessWebhook,
      this.storeTransactionFailureWebhook,
      this.storeTransactionErrorWebhook);

    this.storeService.storeAdminRegistration(store).subscribe(
      response => {
        this.router.navigateByUrl('dashboard');
      }
    );
  }

}

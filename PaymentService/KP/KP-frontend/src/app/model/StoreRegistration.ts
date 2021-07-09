export class StoreRegistration {
  constructor(public name: string,
              public parentStoreId: string,
              public storeTransactionSuccessWebhook: string,
              public storeTransactionFailureWebhook: string,
              public storeTransactionErrorWebhook: string) {
  }
}

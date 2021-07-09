export default interface InvoiceItem {
  id: string,
  storeIssuedId: string,
  storeId: string,
  name: string,
  description: string | null,
  amount: number,
  currency: string
}

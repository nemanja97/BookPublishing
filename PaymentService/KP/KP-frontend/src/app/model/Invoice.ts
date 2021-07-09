import InvoiceItem from "./InvoiceItem";

export default interface Invoice {
  id: string,
  storeIssuedId: string,
  storeId: string,
  items: InvoiceItem[],
  amount: number,
  currency: string
}

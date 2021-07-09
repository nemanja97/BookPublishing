import {
  Component,
  ElementRef,
  Inject,
  OnInit,
  ViewChild,
} from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import Invoice from "../model/Invoice";
import { InvoiceService } from "../service/invoice.service";
import { DOCUMENT } from "@angular/common";

@Component({
  selector: "app-invoice-page",
  templateUrl: "./invoice-page.component.html",
  styleUrls: ["./invoice-page.component.scss"],
})
export class InvoicePageComponent implements OnInit {
  private id: string;
  public loadingData: boolean = true;
  public error: string = "";
  public invoice: Invoice;
  @ViewChild("alert", { static: true }) alert: ElementRef;

  constructor(
    private invoiceService: InvoiceService,
    private route: ActivatedRoute,
    @Inject(DOCUMENT) private document: Document
  ) {}
  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get("id");
    this.invoiceService.getInvoice(this.id).subscribe(
      (res) => {
        console.log(res);
        this.invoice = res;
        this.loadingData = false;
      },
      (err) => {
        console.log(err);
        this.createAlert("Uh oh! Could not retrieve the invoice.");
      }
    );
  }

  closeAlert() {
    this.error = "";
    this.alert.nativeElement.classList.remove("show");
  }

  createAlert(message: string) {
    this.error = message;
    this.alert.nativeElement.classList.append("show");
  }

  onBankClick() {
    this.loadingData = true;
    this.invoiceService.payViaBank(this.id).subscribe(
      (res: any) => {
        this.loadingData = false;
        console.log(res);
        const link = this.document.createElement("a");
        link.target = "_blank";
        link.href = res.paymentUrl;
        link.setAttribute("visibility", "hidden");
        link.click();
      },
      (err) => {
        this.loadingData = false;
        this.createAlert(
          err.error
            ? err.error
            : "Could not establish a connection with the banking service"
        );
      }
    );
  }

  onPayPalClick() {
    this.loadingData = true;
    this.invoiceService.payViaPayPal(this.id).subscribe(
      (res: any) => {
        this.loadingData = false;
        console.log(res);
        const link = this.document.createElement("a");
        link.target = "_blank";
        link.href = res.paymentUrl;
        link.setAttribute("visibility", "hidden");
        link.click();
      },
      (err) => {
        this.loadingData = false;
        this.createAlert(
          err.error
            ? err.error
            : "Could not establish a connection with the PayPal service"
        );
      }
    );
  }

  onBitcoinClick() {
    this.loadingData = true;
    this.invoiceService.payViaBitcoin(this.id).subscribe(
      (res: any) => {
        this.loadingData = false;
        console.log(res);
        const link = this.document.createElement("a");
        link.target = "_blank";
        link.href = res.url;
        link.setAttribute("visibility", "hidden");
        link.click();
      },
      (err) => {
        this.loadingData = false;
        this.createAlert(
          err.error
            ? err.error
            : "Could not establish a connection with the BitPay service"
        );
      }
    );
  }
}

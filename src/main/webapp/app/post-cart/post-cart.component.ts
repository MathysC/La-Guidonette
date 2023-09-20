import { Component, OnInit } from '@angular/core';
import { AccountService } from '../core/auth/account.service';
import { Router } from '@angular/router';
import { TorderService } from '../entities/torder/service/torder.service';
import { NewOrderLine } from '../entities/order-line/order-line.model';
import { ITorder } from '../entities/torder/torder.model';
import { CartContentService } from '../services/cart-content.service';
import { StateStorageService } from '../core/auth/state-storage.service';

@Component({
  selector: 'jhi-post-cart',
  templateUrl: './post-cart.component.html',
  styleUrls: ['../styles.scss'],
})
export class PostCartComponent implements OnInit {
  postOrder: NewOrderLine[] | null = null;
  itorder: ITorder | null = null;
  step = 1;
  canValidate = false;
  canOrder = true;
  constructor(
    public torderService: TorderService,
    public accountService: AccountService,
    public cartContentService: CartContentService,
    private router: Router,
    private stateStorageService: StateStorageService
  ) {}

  ngOnInit(): void {
    if (!this.accountService.isAuthenticated()) {
      this.stateStorageService.storeUrl('order');
      this.router.navigate(['login']);
    }
  }

  handlePaymentValidation(status: boolean): void {
    this.canValidate = status;
  }

  handleCanOrder(status: boolean): void {
    this.canOrder = status;
  }

  nextStep(): void {
    if (this.step === 1) {
      this.step++;
    } else if (this.step === 2) {
      if (this.canValidate) {
        this.validateOrder();
      }
    }
  }

  previousStep(): void {
    this.step--;
    if (this.step === 0) {
      this.router.navigate(['/cart']);
    }
  }

  validateOrder(): void {
    this.postOrder = this.createOrderlines();
    this.torderService.createOrderFromProducts(this.postOrder).subscribe(data => {
      this.itorder = data;
      this.step = 3;
      this.cartContentService.removeAll();
    });
  }

  goToHome(): void {
    this.router.navigate(['']);
  }

  goToCart(): void {
    this.router.navigate(['/cart']);
  }

  private createOrderlines(): NewOrderLine[] {
    return this.cartContentService.getNewOrderlines();
  }
}

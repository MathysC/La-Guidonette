import { Component, OnInit } from '@angular/core';
import { CartContentService } from '../services/cart-content.service';

import { IProduct } from '../entities/product/product.model';
import { AccountService } from '../core/auth/account.service';
import { CoupleProductQuantity } from '../entities/dto/CoupleProductQuantity';

@Component({
  selector: 'jhi-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['../styles.scss'],
})
export class CartComponent {
  constructor(public cartService: CartContentService, public account: AccountService) {}
  deleteCart(): void {
    this.cartService.removeAll();
  }

  deleteItem(prod: IProduct): void {
    this.cartService.removeFromCart(prod);
  }
  addItem(prod: IProduct): void {
    this.cartService.addToCart(prod);
  }
}

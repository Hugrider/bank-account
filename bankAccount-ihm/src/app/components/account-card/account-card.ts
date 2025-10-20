import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Account } from '../../models/Account';
import { CurrencyPipe } from '@angular/common';
import { AccountNamePipe } from '../../utils/account-name-pipe';

@Component({
  selector: 'app-account-card',
  imports: [CurrencyPipe, AccountNamePipe],
  templateUrl: './account-card.html',
  styleUrl: './account-card.scss',
})
export class AccountCard {
  @Input() account!: Account;
  @Output() select = new EventEmitter<Account>();

  onClick(): void {
    this.select.emit(this.account);
  }
}

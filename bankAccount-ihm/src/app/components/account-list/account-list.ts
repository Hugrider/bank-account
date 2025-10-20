import { Component, Input, OnChanges, Output, EventEmitter, SimpleChanges } from '@angular/core';
import { Account } from '../../models/Account';
import { AccountService } from '../../services/account.service';
import { AccountCard } from '../account-card/account-card';

@Component({
  selector: 'app-account-list',
  standalone: true,
  imports: [AccountCard],
  templateUrl: './account-list.html',
  styleUrl: './account-list.scss',
})
export class AccountList implements OnChanges {
  checkingAccounts: Account[] = [];
  savingsAccounts: Account[] = [];

  @Input() accounts: Account[] = [];
  @Output() selected = new EventEmitter<Account>();

  onAccountSelected(account: Account) {
    this.selected.emit(account);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['accounts']) {
      this.checkingAccounts = this.accounts.filter((account) => account.type === 'CHECKING');
      this.savingsAccounts = this.accounts.filter((account) => account.type === 'SAVINGS');
    }
  }
}

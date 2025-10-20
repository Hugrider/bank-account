import { Component, OnInit } from '@angular/core';
import { AccountList } from '../account-list/account-list';
import { OperationForm } from '../operation-form/operation-form';
import { AccountService } from '../../services/account.service';
import { Account } from '../../models/Account';
import { AccountStatement } from '../account-statement/account-statement';

@Component({
  selector: 'app-board',
  standalone: true,
  imports: [AccountList, OperationForm, AccountStatement],
  templateUrl: './board.html',
  styleUrl: './board.scss',
})
export class Board implements OnInit {
  accounts: Account[] = [];
  selectedAccount: string | null = null;
  loading = true;
  error: string | null = null;

  constructor(private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountService.getAccounts().subscribe({
      next: (accounts) => {
        this.accounts = accounts;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erreur lors du chargement des comptes courants';
        this.loading = false;
      },
    });
  }

  onAccountSelected(account: Account) {
    this.selectedAccount = account.number;
  }
}

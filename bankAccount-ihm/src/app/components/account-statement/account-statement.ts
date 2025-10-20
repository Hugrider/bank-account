import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { AccountService } from '../../services/account.service';
import { AccountStatement as AccountStatementType } from '../../models/AccountStatement';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { AccountNamePipe } from '../../utils/account-name-pipe';
import { OperationTypePipe } from '../../utils/operation-type-pipe';

@Component({
  selector: 'app-account-statement',
  imports: [DatePipe, CurrencyPipe, AccountNamePipe, OperationTypePipe],
  templateUrl: './account-statement.html',
  styleUrl: './account-statement.scss',
})
export class AccountStatement implements OnChanges {
  accountStatement: AccountStatementType | null = null;
  loading = true;
  error: string | null = null;
  @Input() accountNumber: string | null = null;

  constructor(private accountService: AccountService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['accountNumber'] && this.accountNumber) {
      this.accountService.getAccountStatement(this.accountNumber).subscribe({
        next: (statement) => {
          this.accountStatement = statement;
          this.loading = false;
        },
        error: (err) => {
          this.error = 'Erreur lors du chargement des comptes courants';
          this.loading = false;
        },
      });
    }
  }
}

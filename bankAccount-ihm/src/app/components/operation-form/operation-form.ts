import { Component, Input } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Account } from '../../models/Account';
import { AccountService } from '../../services/account.service';
import { OperationForm as OperationFormType } from '../../models/Operation';

@Component({
  selector: 'app-operation-form',
  imports: [ReactiveFormsModule],
  templateUrl: './operation-form.html',
  styleUrl: './operation-form.scss',
})
export class OperationForm {
  operationForm = new FormGroup({
    accountNumber: new FormControl<string | null>(null, Validators.required),
    amount: new FormControl<number | null>(null, [Validators.required, Validators.min(0.01)]),
  });

  message: string | null = null;

  @Input() accounts: Account[] = [];

  constructor(private accountService: AccountService) {}

  performOperation(type: 'deposit' | 'withdraw'): void {
    console.log('couccou');

    if (this.operationForm.invalid) {
      this.message = 'Veuillez sélectionner un compte et saisir un montant valide.';
      return;
    }

    const { accountNumber, amount } = this.operationForm.value;

    const operation: OperationFormType = {
      accountNumber: accountNumber!,
      amount: amount!,
    };

    const request =
      type === 'deposit'
        ? this.accountService.deposit(operation)
        : this.accountService.withdraw(operation);

    request.subscribe({
      next: (updated) => {
        const actionLabel = type === 'deposit' ? 'Dépôt' : 'Retrait';
        this.message = `${actionLabel} de ${operation.amount}€ sur le compte ${updated.number} effectué`;
        this.updateAccountBalance(updated);
      },
      error: (err) => {
        console.log(err);

        this.message = err.erro || err.message;
      },
    });
  }

  onDeposit(): void {
    this.performOperation('deposit');
  }

  onWithdraw(): void {
    this.performOperation('withdraw');
  }

  private updateAccountBalance(updated: Account): void {
    const index = this.accounts.findIndex((account) => account.number === updated.number);
    if (index !== -1) this.accounts[index].balance = updated.balance;
  }
}

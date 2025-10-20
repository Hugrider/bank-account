import { Pipe, PipeTransform } from '@angular/core';
import { AccountType } from '../models/Account';

@Pipe({
  name: 'accountName',
})
export class AccountNamePipe implements PipeTransform {
  transform(accountType: AccountType): string {
    if (!accountType) return '';

    return accountType === 'SAVINGS' ? 'Livret' : 'Compte courant';
  }
}

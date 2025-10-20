import { AccountType } from './Account';
import { Operation } from './Operation';

export interface AccountStatement {
  accountNumber: string;
  accountType: AccountType;
  balance: number;
  operations: Operation[];
}

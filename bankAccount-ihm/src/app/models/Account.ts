export interface Account {
  number: string;
  balance: number;
  type: AccountType;
}

export type AccountType = 'CHECKING' | 'SAVINGS';

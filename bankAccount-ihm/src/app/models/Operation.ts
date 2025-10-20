export interface Operation {
  id: number;
  type: OperationType;
  amount: number;
  date: string;
}

export interface OperationForm {
  accountNumber: string;
  amount: number;
}

export type OperationType = 'DEPOSIT' | 'WITHDRAW';

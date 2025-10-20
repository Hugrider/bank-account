import { HttpClient } from '@angular/common/http';
import { Account, AccountType } from '../models/Account';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { OperationForm } from '../models/Operation';
import { AccountStatement } from '../models/AccountStatement';

@Injectable({
  providedIn: 'root',
})
export class AccountService {
  private apiUrl = 'http://localhost:8080/api/accounts';

  constructor(private http: HttpClient) {}

  createAccount(accountType: AccountType): Observable<Account> {
    return this.http.post<Account>(`${this.apiUrl}`, { accountType });
  }

  getAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(`${this.apiUrl}`);
  }

  deposit(operation: OperationForm): Observable<Account> {
    return this.http.post<Account>(`${this.apiUrl}/deposit`, operation);
  }

  withdraw(operation: OperationForm): Observable<Account> {
    return this.http.post<Account>(`${this.apiUrl}/withdraw`, operation);
  }

  getAccountStatement(accountNumber: string): Observable<AccountStatement> {
    return this.http.get<AccountStatement>(`${this.apiUrl}/${accountNumber}/statement`);
  }
}

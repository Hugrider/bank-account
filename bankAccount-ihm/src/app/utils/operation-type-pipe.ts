import { Pipe, PipeTransform } from '@angular/core';
import { OperationType } from '../models/Operation';

@Pipe({
  name: 'operationType',
})
export class OperationTypePipe implements PipeTransform {
  transform(operationType: OperationType): string {
    if (!operationType) return '';

    return operationType === 'DEPOSIT' ? 'Dépot' : 'Retrait';
  }
}

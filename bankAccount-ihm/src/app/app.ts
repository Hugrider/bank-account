import { Component, signal } from '@angular/core';
import { Header } from './components/header/header';
import { Board } from './components/board/board';

@Component({
  selector: 'app-root',
  imports: [Header, Board],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {}

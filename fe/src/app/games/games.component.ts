import { Component } from '@angular/core';
import { Game } from '../model/game';

import { GameService } from '../services/game.service';

@Component({
  selector: 'app-games',
  templateUrl: './games.component.html',
  styleUrls: ['./games.component.css']
})
export class GamesComponent {

  constructor(private gameService: GameService) {}

  games: Game[] = [];
  selectedGame?: Game;

  ngOnInit(): void {
    this.refreshGames();
  }

  refreshGames(): void {
    this.gameService.getGames()
    .subscribe(games => this.games = games);
  }

  onSelect(game: Game): void {
    this.selectedGame = game;
  }
}



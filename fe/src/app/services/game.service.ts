import { Injectable } from '@angular/core';
import { Game } from '../model/game';

import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor() { }

  getGames(): Observable<Game[]> {
    return of([{id: 1,name: '2022'},{id: 2,name: '2023'}]); // ToDo: get from BE
  }
}

import { Injectable } from '@angular/core';
import { Game } from '../model/game';

import { Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  static readonly gamesListPath = '/game';

  constructor(private http: HttpClient) { }

  getGames(): Observable<Game[]> {
    return this.http.get<Game[]>('http://localhost:8080' + GameService.gamesListPath)
    //return of([{id: 1,name: '2022'},{id: 2,name: '2023'}]); // ToDo: get from BE
  }
}

import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, CommonModule],
  template: 
    <nav class="nav">
      <a routerLink="/users" routerLinkActive="active">Users</a>
      <a routerLink="/login" routerLinkActive="active">Login</a>
      <a routerLink="/register" routerLinkActive="active">Register</a>
    </nav>
    <main class="container">
      <router-outlet></router-outlet>
    </main>
  ,
  styles: [
    .nav { display:flex; gap:12px; padding:12px; border-bottom:1px solid #ddd; }
    .container { padding:16px; }
    a.active { font-weight: 600; }
  ]
})
export class AppComponent {}

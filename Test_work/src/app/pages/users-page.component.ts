import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { ReqresApiService } from '../../services/reqres-api.service';
import { User, Resource } from '../../models';

@Component({
  standalone: true,
  selector: 'app-users-page',
  imports: [CommonModule, RouterModule],
  template: `
    <h1>Users (page 2)</h1>

    <ng-container *ngIf="loadingUsers; else usersBlock">
      <p>Loading users...</p>
    </ng-container>
    <ng-template #usersBlock>
      <p *ngIf="usersError" class="error">{{ usersError }}</p>
      <div class="users">
        <div class="user-card" *ngFor="let u of users" (click)="openUser(u.id)">
          <img [src]="u.avatar" [alt]="u.first_name" />
          <div>
            <div class="name">{{ u.first_name }} {{ u.last_name }}</div>
            <div class="email">{{ u.email }}</div>
          </div>
          <button class="delete" (click)="onDelete(u.id); $event.stopPropagation()">Delete</button>
        </div>
      </div>
      <p *ngIf="!users?.length">No users</p>
    </ng-template>

    <h2>Resources</h2>
    <ng-container *ngIf="loadingResources; else resBlock">
      <p>Loading resources...</p>
    </ng-container>
    <ng-template #resBlock>
      <p *ngIf="resourcesError" class="error">{{ resourcesError }}</p>
      <ul class="resources">
        <li *ngFor="let r of resources" [style.background]="r.color">
          <span>{{ r.name }} ({{ r.year }})</span>
          <small>Pantone: {{ r.pantone_value }}</small>
        </li>
      </ul>
    </ng-template>
  `,
  styles: [`
    .users { display:grid; grid-template-columns: repeat(auto-fill, minmax(260px,1fr)); gap:12px; }
    .user-card { display:flex; align-items:center; gap:12px; padding:10px; border:1px solid #ddd; border-radius:8px; cursor:pointer; background:#fff; }
    .user-card img { width:56px; height:56px; border-radius:50%; object-fit:cover; }
    .name { font-weight:600; }
    .email { color:#555; font-size: 13px; }
    .delete { margin-left:auto; background:
    .resources { list-style:none; display:grid; grid-template-columns: repeat(auto-fill, minmax(200px,1fr)); gap:8px; padding:0; }
    .resources li { padding:10px; border-radius:6px; color:#000; display:flex; flex-direction:column; }
    .error { color: #c62828; }
  `]
})
export class UsersPageComponent implements OnInit {
  users: User[] = [];
  resources: Resource[] = [];
  loadingUsers = false;
  loadingResources = false;
  usersError = '';
  resourcesError = '';

  constructor(private api: ReqresApiService, private router: Router) {}

  ngOnInit(): void {
    this.fetchUsers();
    this.fetchResources();
  }

  fetchUsers() {
    this.loadingUsers = true;
    this.usersError = '';
    this.api.getUsers(2).subscribe({
      next: res => { this.users = res.data; this.loadingUsers = false; },
      error: err => { this.usersError = 'Failed to load users'; this.loadingUsers = false; console.error(err); }
    });
  }

  fetchResources() {
    this.loadingResources = true;
    this.resourcesError = '';
    this.api.getResources().subscribe({
      next: res => { this.resources = res.data; this.loadingResources = false; },
      error: err => { this.resourcesError = 'Failed to load resources'; this.loadingResources = false; console.error(err); }
    });
  }

  openUser(id: number) {
    this.router.navigate(['/users', id]);
  }

  onDelete(id: number) {
    if (!confirm('Delete this user from the list?')) return;
    this.api.deleteUser(id).subscribe({
      next: () => {
        // API возвращает 204 и ничего не меняет на сервере — удаляем локально
        this.users = this.users.filter(u => u.id !== id);
      },
      error: err => {
        alert('Failed to delete user');
        console.error(err);
      }
    });
  }
}
    
#ffebee; border:1px solid #ef9a9a; border-radius:6px; padding:6px 10px; cursor:pointer; }

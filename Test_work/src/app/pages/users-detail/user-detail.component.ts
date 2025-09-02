import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ReqresApiService } from '../../services/reqres-api.service';
import { User, UpdateUserPayload } from '../../models';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  standalone: true,
  selector: 'app-user-detail',
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  template: `
    <button class="back" (click)="router.navigate(['/users'])">← Back</button>

    <ng-container *ngIf="loading; else content">
      <p>Loading...</p>
    </ng-container>
    <ng-template #content>
      <p *ngIf="error" class="error">{{ error }}</p>
      <div *ngIf="user" class="card">
        <img [src]="user.avatar" [alt]="user.first_name" />
        <div class="info">
          <h2>{{ user.first_name }} {{ user.last_name }}</h2>
          <p>{{ user.email }}</p>
        </div>
      </div>

      <form [formGroup]="form" (ngSubmit)="onSubmit()" class="form">
        <h3>Edit (PUT /users/{{ userId }})</h3>
        <label>
          Name
          <input type="text" formControlName="name" />
        </label>
        <label>
          Job
          <input type="text" formControlName="job" />
        </label>
        <button type="submit" [disabled]="form.invalid || saving">
          {{ saving ? 'Saving...' : 'Save' }}
        </button>
        <p *ngIf="savedAt" class="muted">Updated at: {{ savedAt }}</p>
      </form>
    </ng-template>
  `,
  styles: [`
    .back { margin-bottom: 10px; }
    .card { display:flex; gap:16px; align-items:center; padding:12px; border:1px solid #ddd; border-radius:8px; margin-bottom:16px; }
    .card img { width:80px; height:80px; border-radius:50%; object-fit:cover; }
    .form { display:grid; gap:8px; max-width:360px; }
    label { display:grid; gap:6px; }
    input { padding:8px; border:1px solid #ccc; border-radius:6px; }
    .error { color:#c62828; }
    .muted { color:#666; font-size:12px; }
  `]
})
export class UserDetailComponent implements OnInit {
  userId!: number;
  user?: User;
  loading = false;
  error = '';
  saving = false;
  savedAt = '';
name: ['', [Validators.required, Validators.minLength(2)]],
    job: ['', [Validators.required, Validators.minLength(2)]],
  });

  constructor(
    private route: ActivatedRoute,
    public router: Router,
    private api: ReqresApiService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.userId = Number(this.route.snapshot.paramMap.get('id'));
    this.fetchUser();
  }

  fetchUser() {
    this.loading = true;
    this.error = '';
    this.api.getUser(this.userId).subscribe({
      next: res => {
        this.user = res.data;
        // Предзаполнить форму — name соберём из first_name + last_name
        const name = ${this.user.first_name} ${this.user.last_name}.trim();
        this.form.patchValue({ name, job: 'Unknown' });
        this.loading = false;
      },
      error: err => {
        this.error = 'Failed to load user';
        this.loading = false;
        console.error(err);
      }
    });
  }

  onSubmit() {
    if (this.form.invalid || !this.user) return;
    const payload: UpdateUserPayload = this.form.getRawValue() as UpdateUserPayload;
    this.saving = true;
    this.api.updateUser(this.userId, payload).subscribe({
      next: res => {
        this.savedAt = res.updatedAt;
        this.saving = false;
      },
      error: err => {
        alert('Failed to update user');
        this.saving = false;
        console.error(err);
      }
    });
  }
}
  form = this.fb.group({

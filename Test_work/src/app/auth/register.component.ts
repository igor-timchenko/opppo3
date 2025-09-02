import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ReqresApiService } from '../services/reqres-api.service';

@Component({
  standalone: true,
  selector: 'app-register',
  imports: [CommonModule, ReactiveFormsModule],
  template: 
    <h1>Register</h1>
    <form [formGroup]="form" (ngSubmit)="onSubmit()" class="form">
      <label>Email <input type="email" formControlName="email"></label>
      <label>Password <input type="password" formControlName="password"></label>
      <button type="submit" [disabled]="form.invalid || loading">{{ loading ? '...' : 'Register' }}</button>
      <p *ngIf="token" class="ok">Token: {{ token }}</p>
      <p *ngIf="error" class="error">{{ error }}</p>
    </form>
  ,
  styles: [.form{display:grid;gap:8px;max-width:320px}.ok{color:#2e7d32}.error{color:#c62828}]
})
export class RegisterComponent {
  form = this.fb.group({
    email: ['eve.holt@reqres.in', [Validators.required, Validators.email]],
    password: ['pistol', [Validators.required]],
  });
  loading = false;
  token = '';
  error = '';

  constructor(private fb: FormBuilder, private api: ReqresApiService) {}

  onSubmit() {
    if (this.form.invalid) return;
    this.loading = true; this.token = ''; this.error = '';
    this.api.register(this.form.getRawValue()!).subscribe({
      next: r => { this.token = r.token; localStorage.setItem('token', r.token); this.loading = false; },
      error: err => { this.error = 'Register failed'; this.loading = false; console.error(err); }
    });
  }
}

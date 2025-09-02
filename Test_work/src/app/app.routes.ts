import { Routes } from '@angular/router';
import { UsersPageComponent } from './pages/users/users-page.component';
import { UserDetailComponent } from './pages/user-detail/user-detail.component';
import { LoginComponent } from './auth/login.component';
import { RegisterComponent } from './auth/register.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'users' },
  { path: 'users', component: UsersPageComponent },
  { path: 'users/:id', component: UserDetailComponent },
  // необязательно:
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: '**', redirectTo: 'users' },
];

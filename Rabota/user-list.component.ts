import { Component, OnInit } from '@angular/core';
   import { UserService } from '../../services/user.service';
   import { Router } from '@angular/router';

   @Component({
     selector: 'app-user-list',
     templateUrl: './user-list.component.html',
     styleUrls: ['./user-list.component.css']
   })
   export class UserListComponent implements OnInit {
     users: any[] = [];

     constructor(private userService: UserService, private router: Router) {}

     ngOnInit(): void {
       this.userService.getUsers(2).subscribe(data => {
         this.users = data.data;
       });
     }

     viewUser(id: number) {
       this.router.navigate(['/user', id]);
     }

     deleteUser(id: number) {
       this.userService.deleteUser(id).subscribe(() => {
         this.users = this.users.filter(user => user.id !== id);
       });
     }
   }

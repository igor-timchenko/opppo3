import { Component, OnInit } from '@angular/core';
   import { ActivatedRoute, Router } from '@angular/router';
   import { UserService } from '../../services/user.service';

   @Component({
     selector: 'app-user-detail',
     templateUrl: './user-detail.component.html',
     styleUrls: ['./user-detail.component.css']
   })
   export class UserDetailComponent implements OnInit {
     user: any;

     constructor(
       private route: ActivatedRoute,
       private userService: UserService,
       private router: Router
     ) {}

     ngOnInit(): void {
       const id = +this.route.snapshot.paramMap.get('id');
       this.userService.getUser(id).subscribe(data => {
         this.user = data.data;
       });
     }

     updateUser() {
       const updatedData = {
         name: this.user.first_name,
         job: 'Updated Job' // Здесь можно добавить поля, которые вы хотите обновить
       };
       this.userService.updateUser(this.user.id, updatedData).subscribe(() => {
         alert('Данные пользователя обновлены!');
         this.router.navigate(['/users']);
       });
     }
   }

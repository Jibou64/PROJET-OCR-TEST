import { Component, OnInit, NgZone } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { User } from '../../interfaces/user.interface';
import { SessionService } from '../../services/session.service';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.scss']
})
export class MeComponent implements OnInit {

  public user: User | undefined;

  constructor(
    private router: Router,
    private sessionService: SessionService,
    private matSnackBar: MatSnackBar,
    private userService: UserService,
    private ngZone: NgZone  // Ajout de NgZone
  ) { }

  public ngOnInit(): void {
    this.userService
      .getById(this.sessionService.sessionInformation!.id.toString())
      .subscribe((user: User) => {
        this.ngZone.run(() => {  // Assurez-vous que la mise à jour de l'interface utilisateur se fait dans la zone Angular
          this.user = user;
        });
      });
  }

  public back(): void {
    window.history.back();
  }

  public delete(): void {
    this.userService
      .delete(this.sessionService.sessionInformation!.id.toString())
      .subscribe((_) => {
        this.ngZone.run(() => {  // Assurez-vous que la navigation se fait dans la zone Angular
          this.matSnackBar.open("Your account has been deleted !", 'Close', { duration: 3000 });
          this.sessionService.logOut();
          this.router.navigate(['/']);
        });
      });
  }
}

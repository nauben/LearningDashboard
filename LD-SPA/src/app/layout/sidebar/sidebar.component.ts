import { Component, OnInit } from '@angular/core';
import { AccountService} from '../../_services/account.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  constructor(
    private accountService: AccountService,
  ) { }

  ngOnInit(): void {
  }

  onLogout(){
    console.log('Bye');
    this.accountService.logout();
  }

}

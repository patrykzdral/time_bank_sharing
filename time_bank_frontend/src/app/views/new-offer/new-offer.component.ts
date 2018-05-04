import { Component, OnInit } from '@angular/core';
import {Offer} from "../../dto/offer.model";
import {Router} from "@angular/router";
import {OfferService} from "../../services/offer/offer.service";
import {Globals} from "../../logged_user/logged_user";
import {UserService} from "../../services/user/user.service";

@Component({
  selector: 'app-new-offer',
  templateUrl: './new-offer.component.html',
  styleUrls: ['./new-offer.component.css']
})
export class NewOfferComponent implements OnInit {

    offer: Offer = new Offer();
    constructor(public router: Router , private offerService: OfferService,private userService:UserService) { }

    ngOnInit() {
    }

    refresh(){
        this.router.navigate(['/']);
    }

    createOffer() {
        if(this.offer.type){
            this.offer.giver=this.userService.user.id;
        }
        else{
            this.offer.type=false;
            this.offer.receiver=this.userService.user.id;
        }
        console.log("KURWA");
        console.log(this.userService.user);
        console.log("KURWA");

        this.offerService.saveOffer(this.offer).then(
            () => {
                this.router.navigateByUrl('/main');
            }, () => {
                this.router.navigateByUrl('/events'); });
    }
}

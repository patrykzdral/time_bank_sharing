import { Component, OnInit } from '@angular/core';
import {OfferService} from "../../services/offer/offer.service";
import {Router} from "@angular/router";
import {Offer} from "../../dto/offer.model";
import {HttpClient} from "@angular/common/http";
import {UserService} from "../../services/user/user.service";

@Component({
  selector: 'app-receive-offer',
  templateUrl: './receive-offer.component.html',
  styleUrls: ['./receive-offer.component.css']
})
export class ReceiveOfferComponent implements OnInit {

    public offers: Offer[];
    constructor(private eventService: OfferService, public router: Router , private http: HttpClient,private userService:UserService) { }


    ngOnInit() {

        this.eventService.getActiveOffers().subscribe(data => {
            this.offers = data;
        });

    }

    enroll(id) {
        this.http.put('/offers/'+id,this.userService.user.id)
            .subscribe(res => {
                    let id = res['id'];
                    this.router.navigate(['/new-offer']);
                }, (err) => {
                    console.log(err);
                }
            );
    }
}

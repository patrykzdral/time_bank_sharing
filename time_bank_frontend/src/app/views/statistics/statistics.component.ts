import { Component, OnInit } from '@angular/core';
import {Offer} from "../../dto/offer.model";
import {OfferService} from "../../services/offer/offer.service";
import {Router} from "@angular/router";
import {UserService} from "../../services/user/user.service";
import {HttpClient} from "@angular/common/http";
import {Statistics} from "../../dto/statistics.model";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.css']
})
export class StatisticsComponent implements OnInit {

    public statistics: Statistics;
    constructor(private eventService: OfferService, public router: Router , private http: HttpClient,private userService:UserService) { }


    ngOnInit() {
        this.getStatistics().subscribe(data => {
            this.statistics = data;
        });
    }

    getStatistics() : any{
        return this.http.get('/offers/statistics/'+this.userService.user.id,this.userService.user.id);
    }
}

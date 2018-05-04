import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Offer} from "../../dto/offer.model";

@Injectable()
export class OfferService {

    offer: Offer;
    offers: Array<Offer>;
    constructor(private http: HttpClient) { }

    saveOffer(offer: Offer) {
        const promise = new Promise((resolve, reject) => {
            this.http.post('/offers', offer).toPromise().then(res => {
                    if ( res != null) {
                        resolve();
                    } else { reject(); }
                },
                (err) => {
                    console.log(err);
                });
        });
        return promise;
    }

}

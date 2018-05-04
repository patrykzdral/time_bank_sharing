package com.pwr.patrykzdral.timebank.database.service;

import com.pwr.patrykzdral.timebank.database.entity.Offer;
import com.pwr.patrykzdral.timebank.database.entity.Statistics;
import com.pwr.patrykzdral.timebank.database.entity.User;

public interface OfferService extends CRUDService<Offer> {
    Statistics getUserStatistics(User user);

}

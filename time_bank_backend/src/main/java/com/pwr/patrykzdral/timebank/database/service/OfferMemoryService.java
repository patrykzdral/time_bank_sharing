package com.pwr.patrykzdral.timebank.database.service;

import com.pwr.patrykzdral.timebank.database.entity.Offer;
import com.pwr.patrykzdral.timebank.database.entity.Statistics;
import com.pwr.patrykzdral.timebank.database.entity.User;

import java.util.List;

public interface OfferMemoryService extends CRUDService<Offer> {
    Statistics getUserStatistics(User user);
    List<Offer> findActiveOffers(User user);
    List<Offer> findGivenOffers(User user);
    List<Offer> findTakenOffers(User user);
}

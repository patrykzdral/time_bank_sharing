package com.pwr.patrykzdral.timebank.database.repository;

import com.pwr.patrykzdral.timebank.database.entity.Offer;
import com.pwr.patrykzdral.timebank.database.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
    List<Offer> findOffersByGiver(User giver);
    List<Offer> findOffersByReceiver(User receiver);
}

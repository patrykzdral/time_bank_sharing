package com.pwr.patrykzdral.timebank.database.service.impl;

import com.pwr.patrykzdral.timebank.database.entity.Offer;
import com.pwr.patrykzdral.timebank.database.entity.Statistics;
import com.pwr.patrykzdral.timebank.database.entity.User;
import com.pwr.patrykzdral.timebank.database.service.OfferXMLService;
import com.pwr.patrykzdral.timebank.file_read_write.Database;
import com.pwr.patrykzdral.timebank.file_read_write.DatabaseXmlParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OfferXmlServiceImpl implements OfferXMLService {
    @Override
    public List<Offer> findAll() {
        try {
            Database database = DatabaseXmlParser.readFromXmlFile();
            return database.getOffers();
        } catch (JAXBException e) {
            return null;
        }
    }

    @Override
    public Offer save(Offer entity) {
        try {
            Database database = DatabaseXmlParser.readFromXmlFile();

            List<Offer> offers = database.getOffers();
            if (offers==null || offers.size()==0)
                entity.setId(1);
            else{
                Offer offerWithTheBiggestId = offers.stream().max(Comparator.comparing(Offer::getId)).get();
                entity.setId(offerWithTheBiggestId.getId()+1);
            }

            database.addOffer(entity);
            DatabaseXmlParser.writeToXmlFile(database);
            return entity;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            Database database = DatabaseXmlParser.readFromXmlFile();
            database.deleteOffer(database.findOfferById(id));
            DatabaseXmlParser.writeToXmlFile(database);
        } catch (JAXBException ignored) {
        }
    }

    @Override
    public void delete(Offer entity) {
        try {
            Database database = DatabaseXmlParser.readFromXmlFile();
            database.deleteOffer(entity);
            DatabaseXmlParser.writeToXmlFile(database);
        } catch (JAXBException ignored) {
        }
    }

    @Override
    public Offer find(Integer id) {
        try {
            Database database = DatabaseXmlParser.readFromXmlFile();
            return database.findOfferById(id);
        } catch (JAXBException ignored) {
            return null;
        }
    }

    @Override
    public Statistics getUserStatistics(User user) {
        try {
            Database database = DatabaseXmlParser.readFromXmlFile();
            List<Offer> givenOffers = database.findOffersByGiver(user);
            List<Offer> takenOffers = database.findOffersByReceiver(user);

            Integer numberOfGivenOffers = givenOffers.size();
            Integer numberOfTakenOffers = takenOffers.size();

            AtomicReference<Long> numberOfGivenSeconds = new AtomicReference<>(0L);
            givenOffers.forEach(offer -> numberOfGivenSeconds.updateAndGet(v -> v + TimeUnit.MILLISECONDS
                    .toSeconds(offer.getDateTo().getTime() - offer.getDateFrom().getTime())));

            Long givenTimeDays = TimeUnit.SECONDS.toDays(numberOfGivenSeconds.get());
            Long givenTimeHours = TimeUnit.SECONDS.toHours(numberOfGivenSeconds.get()) - (givenTimeDays * 24);
            Long givenTimeMinutes = TimeUnit.SECONDS.toMinutes(numberOfGivenSeconds.get())
                    - (TimeUnit.SECONDS.toHours(numberOfGivenSeconds.get()) * 60);
            Long givenTimeSeconds = TimeUnit.SECONDS.toSeconds(numberOfGivenSeconds.get())
                    - (TimeUnit.SECONDS.toMinutes(numberOfGivenSeconds.get()) * 60);

            AtomicReference<Long> numberOfTakenSeconds = new AtomicReference<>(0L);
            takenOffers.forEach(offer -> numberOfTakenSeconds.updateAndGet(v -> v + TimeUnit.MILLISECONDS
                    .toSeconds(offer.getDateTo().getTime() - offer.getDateFrom().getTime())));

            Long takenTimeDays = TimeUnit.SECONDS.toDays(numberOfTakenSeconds.get());
            Long takenTimeHours = TimeUnit.SECONDS.toHours(numberOfTakenSeconds.get()) - (takenTimeDays * 24);
            Long takenTimeMinutes = TimeUnit.SECONDS.toMinutes(numberOfTakenSeconds.get())
                    - (TimeUnit.SECONDS.toHours(numberOfTakenSeconds.get()) * 60);
            Long takenTimeSeconds = TimeUnit.SECONDS.toSeconds(numberOfTakenSeconds.get())
                    - (TimeUnit.SECONDS.toMinutes(numberOfTakenSeconds.get()) * 60);

            return new Statistics(numberOfGivenOffers, numberOfTakenOffers, givenTimeDays, givenTimeHours, givenTimeMinutes,
                    givenTimeSeconds, takenTimeDays, takenTimeHours, takenTimeMinutes, takenTimeSeconds);
        } catch (JAXBException e) {
           return null;
        }
    }
}

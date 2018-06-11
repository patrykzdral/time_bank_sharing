package com.pwr.patrykzdral.timebank.controller;

import com.pwr.patrykzdral.timebank.database.entity.Offer;
import com.pwr.patrykzdral.timebank.database.entity.Statistics;
import com.pwr.patrykzdral.timebank.database.entity.User;
import com.pwr.patrykzdral.timebank.database.service.*;

import com.google.gson.Gson;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static java.lang.Math.toIntExact;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.StreamSupport;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/offers")
public class OfferController {

    private final OfferService offerService;
    private final UserService userService;
    private final UserXMLService userXMLService;
    private final OfferXMLService offerXMLService;
    private final OfferMemoryService offerMemoryService;
    private final UserMemoryService userMemoryService;


    @Autowired
    public OfferController(OfferService offerService, UserService userService, UserXMLService userXMLService, OfferXMLService offerXMLService, OfferMemoryService offerMemoryService, UserMemoryService userMemoryService) {
        this.offerService = offerService;
        this.userService = userService;
        this.userXMLService = userXMLService;
        this.offerXMLService = offerXMLService;
        this.offerMemoryService = offerMemoryService;
        this.userMemoryService = userMemoryService;
    }


    @RequestMapping(method = RequestMethod.GET,  value = "/active")
    public Iterable<Offer> getActiveOffers() {

        Iterable<Offer> offers=  offerService.findAll();
        return () -> StreamSupport.stream(offers.spliterator(), false)
                .filter(text -> text.getGiver()==null||text.getReceiver()==null)
                .iterator();
    }



    @RequestMapping(method=RequestMethod.PUT, value="/{id}")
    public Offer update(@PathVariable String id, @RequestBody String userId) {
        Offer o = offerService.find(Integer.parseInt(id));
        User user= userService.find(Integer.parseInt(userId));
         if(o.getReceiver()==null)  {
           o.setReceiver(user);
         }
         if(o.getGiver()==null) {
             o.setGiver(user);
         }
        offerService.save(o);
        return o;
    }

    @RequestMapping(method = RequestMethod.POST,  consumes = {APPLICATION_JSON_VALUE})
    public JSONObject saveOffer(@RequestBody String stringToParse){
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(stringToParse);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        DateTimeFormatter format = DateTimeFormatter.ISO_DATE_TIME;
        java.sql.Timestamp sqlFrom =null;
        java.sql.Timestamp sqlTo =null;
        try {
            LocalDateTime dateFrom = LocalDateTime.parse((String)json.get("dateFrom"), format);
            sqlFrom = Timestamp.valueOf(dateFrom);
            LocalDateTime dateTo = LocalDateTime.parse((String)json.get("dateTo"), format);
            sqlTo = Timestamp.valueOf(dateTo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Offer offer =new Offer((String)json.get("name"),(String)json.get("description"),sqlFrom,sqlTo,(String)json.get("address"),(Boolean) json.get("type"));
        User user = null;

        if(json.get("giver")!=null){
            user = userService.find(toIntExact((Long) json.get("giver")));
            offer.setGiver(user);
        }
        if(json.get("receiver")!=null){
            user = userService.find(toIntExact((Long)json.get("receiver")));
            offer.setReceiver(user);
        }

        offerService.save(offer);

        return json;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/statistics/{id}", produces = {APPLICATION_JSON_VALUE})
    public Statistics user(@PathVariable Integer id) {
        return offerService.getUserStatistics(userService.find(id));
    }


    @RequestMapping(method = RequestMethod.GET,  value = "/xml/active")
    public Iterable<Offer> getActiveOffersXml() {

        Iterable<Offer> offers=  offerXMLService.findAll();
        return () -> StreamSupport.stream(offers.spliterator(), false)
                .filter(text -> text.getGiver()==null||text.getReceiver()==null)
                .iterator();
    }



    @RequestMapping(method=RequestMethod.PUT, value="/xml/{id}")
    public Offer updateXml(@PathVariable String id, @RequestBody String userId) {
        Offer o = offerXMLService.find(Integer.parseInt(id));
        User user= userXMLService.find(Integer.parseInt(userId));
        if(o.getReceiver()==null)  {
            o.setReceiver(user);
        }
        if(o.getGiver()==null) {
            o.setGiver(user);
        }
        offerXMLService.save(o);
        return o;
    }

    @RequestMapping(method = RequestMethod.POST,  consumes = {APPLICATION_JSON_VALUE}, value="/xml")
    public JSONObject saveOfferXml(@RequestBody String stringToParse){
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(stringToParse);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        DateTimeFormatter format = DateTimeFormatter.ISO_DATE_TIME;
        java.sql.Timestamp sqlFrom =null;
        java.sql.Timestamp sqlTo =null;
        try {
            LocalDateTime dateFrom = LocalDateTime.parse((String)json.get("dateFrom"), format);
            sqlFrom = Timestamp.valueOf(dateFrom);
            LocalDateTime dateTo = LocalDateTime.parse((String)json.get("dateTo"), format);
            sqlTo = Timestamp.valueOf(dateTo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Offer offer =new Offer((String)json.get("name"),(String)json.get("description"),sqlFrom,sqlTo,(String)json.get("address"),(Boolean) json.get("type"));
        User user = null;

        if(json.get("giver")!=null){
            user = userService.find(toIntExact((Long) json.get("giver")));
            offer.setGiver(user);
        }
        if(json.get("receiver")!=null){
            user = userService.find(toIntExact((Long)json.get("receiver")));
            offer.setReceiver(user);
        }

        offerXMLService.save(offer);

        return json;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/xml/statistics/{id}", produces = {APPLICATION_JSON_VALUE})
    public Statistics userXml(@PathVariable Integer id) {
        return offerXMLService.getUserStatistics(userXMLService.find(id));
    }



    @RequestMapping(method = RequestMethod.GET,  value = "/memory/active")
    public Iterable<Offer> getActiveOffersMemory() {

        Iterable<Offer> offers=  offerXMLService.findAll();
        return () -> StreamSupport.stream(offers.spliterator(), false)
                .filter(text -> text.getGiver()==null||text.getReceiver()==null)
                .iterator();
    }



    @RequestMapping(method=RequestMethod.PUT, value="/memory/{id}")
    public Offer updateMemory(@PathVariable String id, @RequestBody String userId) {
        Offer o = offerXMLService.find(Integer.parseInt(id));
        User user= userXMLService.find(Integer.parseInt(userId));
        if(o.getReceiver()==null)  {
            o.setReceiver(user);
        }
        if(o.getGiver()==null) {
            o.setGiver(user);
        }
        offerXMLService.save(o);
        return o;
    }

    @RequestMapping(method = RequestMethod.POST,  consumes = {APPLICATION_JSON_VALUE}, value="/memory")
    public JSONObject saveOfferMemory(@RequestBody String stringToParse){
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(stringToParse);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        DateTimeFormatter format = DateTimeFormatter.ISO_DATE_TIME;
        java.sql.Timestamp sqlFrom =null;
        java.sql.Timestamp sqlTo =null;
        try {
            LocalDateTime dateFrom = LocalDateTime.parse((String)json.get("dateFrom"), format);
            sqlFrom = Timestamp.valueOf(dateFrom);
            LocalDateTime dateTo = LocalDateTime.parse((String)json.get("dateTo"), format);
            sqlTo = Timestamp.valueOf(dateTo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Offer offer =new Offer((String)json.get("name"),(String)json.get("description"),sqlFrom,sqlTo,(String)json.get("address"),(Boolean) json.get("type"));
        User user = null;

        if(json.get("giver")!=null){
            user = userService.find(toIntExact((Long) json.get("giver")));
            offer.setGiver(user);
        }
        if(json.get("receiver")!=null){
            user = userService.find(toIntExact((Long)json.get("receiver")));
            offer.setReceiver(user);
        }

        offerXMLService.save(offer);

        return json;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/memory/statistics/{id}", produces = {APPLICATION_JSON_VALUE})
    public Statistics userMemory(@PathVariable Integer id) {
        return offerMemoryService.getUserStatistics(userMemoryService.find(id));
    }



    // Oferty (DELETE):
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE}, value = "/delete/{id}",
            produces = {APPLICATION_JSON_VALUE})
    public void delete(@PathVariable Integer id) {
        Offer offer = offerService.find(id);
        offerService.delete(offer);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE}, value = "/xml/delete/{id}",
            produces = {APPLICATION_JSON_VALUE})
    public void deleteXml(@PathVariable Integer id) {
        Offer offer = offerXMLService.find(id);
        offerXMLService.delete(offer);
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE}, value = "/memory/delete/{id}",
            produces = {APPLICATION_JSON_VALUE})
    public void deleteMemory(@PathVariable Integer id) {
        Offer offer = offerMemoryService.find(id);
        offerMemoryService.delete(offer);
    }

}

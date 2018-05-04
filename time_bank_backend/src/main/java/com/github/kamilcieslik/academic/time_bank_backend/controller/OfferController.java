package com.github.kamilcieslik.academic.time_bank_backend.controller;

import com.github.kamilcieslik.academic.time_bank_backend.database.entity.Offer;
import com.github.kamilcieslik.academic.time_bank_backend.database.entity.User;
import com.github.kamilcieslik.academic.time_bank_backend.database.service.OfferService;
import com.github.kamilcieslik.academic.time_bank_backend.database.service.UserService;

import com.google.gson.Gson;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static java.lang.Math.toIntExact;

import javax.jws.soap.SOAPBinding;
import javax.persistence.criteria.CriteriaBuilder;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/offers")
public class OfferController {

    private final OfferService offerService;
    private final UserService userService;

    @Autowired
    public OfferController(OfferService offerService, UserService userService) {
        this.offerService = offerService;
        this.userService = userService;
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
        System.out.println(stringToParse);
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(stringToParse);
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        DateTimeFormatter format = DateTimeFormatter.ISO_DATE_TIME;
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //java.util parsed = null;
        java.sql.Timestamp sqlFrom =null;
        java.sql.Timestamp sqlTo =null;
        try {
            LocalDateTime dateFrom = LocalDateTime.parse((String)json.get("dateFrom"), format);
            sqlFrom = Timestamp.valueOf(dateFrom);
            //parsed = format.parse((String)json.get("dateFrom"));
            //sqlFrom = new java.sql.Timestamp(parsed.getTime());
            LocalDateTime dateTo = LocalDateTime.parse((String)json.get("dateTo"), format);
            sqlTo = Timestamp.valueOf(dateTo);
            //parsed =  format.parse((String)json.get("dateTo"));
            //sqlTo = new java.sql.Timestamp(parsed.getTime());

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

}

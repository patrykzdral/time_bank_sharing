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
import java.text.ParseException;
import java.text.SimpleDateFormat;

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

    /*
    @RequestMapping(method = RequestMethod.POST , consumes = {APPLICATION_JSON_VALUE})
    public Offer save(@RequestBody Offer offer) {
        User user =null;
        System.out.println(stringToParse);
        JSONObject obj=null;
        if(obj.get("giver")!=null){
            user = userService.find((int)obj.get("giver"));
            offer.setGiver(user);
        }
        if(obj.get("receiver")!=null){
            user = userService.find((int)obj.get("giver"));
            offer.setReceiver(user);
        }
        offerService.save(offer);
        return offer;
    }
    */

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
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        java.util.Date parsed = null;
        java.sql.Date sqlFrom =null;
        java.sql.Date sqlTo =null;
        try {
            parsed = format.parse((String)json.get("dateFrom"));
            sqlFrom = new java.sql.Date(parsed.getTime());
            parsed =  format.parse((String)json.get("dateTo"));
            sqlTo = new java.sql.Date(parsed.getTime());

        } catch (ParseException e) {
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

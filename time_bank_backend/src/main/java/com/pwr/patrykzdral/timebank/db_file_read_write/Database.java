package com.pwr.patrykzdral.timebank.db_file_read_write;

import com.pwr.patrykzdral.timebank.database.entity.Offer;
import com.pwr.patrykzdral.timebank.database.entity.User;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Database {
    @XmlElementWrapper(name = "users")
    @XmlElement(name = "user")
    private List<User> users;
    @XmlElementWrapper(name = "offers")
    @XmlElement(name = "offer")
    private List<Offer> offers;

    public Database() {
    }

    public Database(List<User> users, List<Offer> offers) {
        this.users = users;
        this.offers = offers;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}

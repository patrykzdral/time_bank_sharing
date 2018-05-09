package com.pwr.patrykzdral.timebank.database.service.impl;

import com.pwr.patrykzdral.timebank.database.entity.User;
import com.pwr.patrykzdral.timebank.database.service.UserXMLService;
import com.pwr.patrykzdral.timebank.file_read_write.Database;
import com.pwr.patrykzdral.timebank.file_read_write.DatabaseXmlParser;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.Comparator;
import java.util.List;

@Service
public class UserXmlServiceImpl implements UserXMLService {
    @Override
    public User findByLogin(String login) {
        try {
            Database database = DatabaseXmlParser.readFromXmlFile();
            return database.findUserByLogin(login);
        } catch (JAXBException ignored) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            Database database = DatabaseXmlParser.readFromXmlFile();
            return database.findUserByEmail(email);
        } catch (JAXBException ignored) {
            return null;
        }
    }

    @Override
    public Boolean checkUserExists(String login, String email) {
        try {
            Database database = DatabaseXmlParser.readFromXmlFile();
            return database.findUserByLogin(login) != null || database.findUserByEmail(email) != null;
        } catch (JAXBException e) {
            return null;
        }
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        try {
            Database database = DatabaseXmlParser.readFromXmlFile();
            return database.findUserByLoginAndPassword(login, password);
        } catch (JAXBException ignored) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        try {
            Database database = DatabaseXmlParser.readFromXmlFile();
            return database.getUsers();
        } catch (JAXBException e) {
            return null;
        }
    }

    @Override
    public User save(User entity) {
        try {
            Database database = DatabaseXmlParser.readFromXmlFile();

            List<User> users = database.getUsers();
            if (users==null || users.size()==0)
                entity.setId(1);
            else{
                User userWithTheBiggestId = users.stream().max(Comparator.comparing(User::getId)).get();
                entity.setId(userWithTheBiggestId.getId()+1);
            }

            database.addUser(entity);
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
            database.deleteUser(database.findUserById(id));
            DatabaseXmlParser.writeToXmlFile(database);
        } catch (JAXBException ignored) {
        }
    }

    @Override
    public void delete(User entity) {
        try {
            Database database = DatabaseXmlParser.readFromXmlFile();
            database.deleteUser(entity);
            DatabaseXmlParser.writeToXmlFile(database);
        } catch (JAXBException ignored) {
        }
    }

    @Override
    public User find(Integer id) {
        try {
            Database database = DatabaseXmlParser.readFromXmlFile();
            return database.findUserById(id);
        } catch (JAXBException ignored) {
            return null;
        }
    }
}

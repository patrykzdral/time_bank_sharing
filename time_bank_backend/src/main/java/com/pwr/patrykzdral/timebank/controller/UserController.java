package com.pwr.patrykzdral.timebank.controller;

import com.pwr.patrykzdral.timebank.database.entity.User;
import com.pwr.patrykzdral.timebank.database.service.UserService;
import com.pwr.patrykzdral.timebank.database.service.UserXMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;
    private final UserXMLService userXMLService;

    @Autowired
    public UserController(UserService userService, UserXMLService userXMLService) {
        this.userService = userService;
        this.userXMLService = userXMLService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = {APPLICATION_JSON_VALUE})
    public User user(@PathVariable Integer id) {
        return userService.find(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/login", produces = {APPLICATION_JSON_VALUE})
    public User userByLoginAndPassword(@RequestParam("login") String login, @RequestParam("password") String password) {
        return userService.findByLoginAndPassword(login, password);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAll", produces = {APPLICATION_JSON_VALUE})
    public Iterable<User> user() {
        return userService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/check", produces = {APPLICATION_JSON_VALUE})
    public boolean checkIfExist(@RequestParam("login") String login, @RequestParam("email") String email) {
        return userService.checkUserExists(login, email);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/update/{id}", produces = {APPLICATION_JSON_VALUE})
    public User update(@PathVariable Integer id, @RequestBody User updatedUser) {
        User user = userService.find(id);
        if (updatedUser.getFirstName() != null)
            user.setFirstName(updatedUser.getFirstName());
        if (updatedUser.getLastName() != null)
            user.setLastName(updatedUser.getLastName());
        if (updatedUser.getPhone() != null)
            user.setPhone(updatedUser.getPhone());
        if (updatedUser.getEmail() != null)
            user.setEmail(updatedUser.getEmail());
        if (updatedUser.getLogin() != null)
            user.setLogin(updatedUser.getLogin());
        if (updatedUser.getPassword() != null)
            user.setPassword(updatedUser.getPassword());

        userService.save(user);
        return updatedUser;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE}, value = "/delete/{id}",
            produces = {APPLICATION_JSON_VALUE})

    public void delete(@PathVariable Integer id) {
        User user = userService.find(id);
        userService.delete(user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User save(@RequestBody User contact) {
        userService.save(contact);
        return contact;
    }

    @RequestMapping(method = RequestMethod.POST ,value = "/xml")
    public User saveToXml(@RequestBody User contact) {
        userXMLService.save(contact);
        return contact;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/xml/{id}", produces = {APPLICATION_JSON_VALUE})
    public User userXml(@PathVariable Integer id) {
        return userXMLService.find(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/xml/login", produces = {APPLICATION_JSON_VALUE})
    public User userByLoginAndPasswordXml(@RequestParam("login") String login, @RequestParam("password") String password) {
        return userXMLService.findByLoginAndPassword(login, password);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/xml/findAll", produces = {APPLICATION_JSON_VALUE})
    public Iterable<User> userXml() {
        return userXMLService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/xml/check", produces = {APPLICATION_JSON_VALUE})
    public boolean checkIfExistXml(@RequestParam("login") String login, @RequestParam("email") String email) {
        return userXMLService.checkUserExists(login, email);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/xml/update/{id}", produces = {APPLICATION_JSON_VALUE})
    public User updateXml(@PathVariable Integer id, @RequestBody User updatedUser) {
        User user = userXMLService.find(id);
        if (updatedUser.getFirstName() != null)
            user.setFirstName(updatedUser.getFirstName());
        if (updatedUser.getLastName() != null)
            user.setLastName(updatedUser.getLastName());
        if (updatedUser.getPhone() != null)
            user.setPhone(updatedUser.getPhone());
        if (updatedUser.getEmail() != null)
            user.setEmail(updatedUser.getEmail());
        if (updatedUser.getLogin() != null)
            user.setLogin(updatedUser.getLogin());
        if (updatedUser.getPassword() != null)
            user.setPassword(updatedUser.getPassword());

        userXMLService.save(user);
        return updatedUser;
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET, RequestMethod.DELETE}, value = "/xml/delete/{id}",
            produces = {APPLICATION_JSON_VALUE})
    public void deleteXml(@PathVariable Integer id) {
        User user = userXMLService.find(id);
        userXMLService.delete(user);
    }


}

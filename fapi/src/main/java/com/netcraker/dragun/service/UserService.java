package com.netcraker.dragun.service;

import com.netcraker.dragun.model.Company;
import com.netcraker.dragun.model.DataUser;
import com.netcraker.dragun.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("customUserDetailsService")
public class UserService implements UserDetailsService {
    private RestTemplate restTemplate;
    @Value("${backend.url}")
    private String backendURL;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public User get(Long id) {
        return restTemplate.getForObject(backendURL + "users/" + id, User.class);
    }

    public List<User> getAll() {
        return Arrays.asList(restTemplate.getForObject(backendURL + "users/", User[].class));
    }

    public User getUserByLogin(String login) {
        return restTemplate.getForObject(backendURL + "users/login/" + login, User.class);
    }
    public User getCompanyByLogin(String login) {
        return restTemplate.getForObject(backendURL + "companies/login/" + login, User.class);
    }

    /*public User create(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return restTemplate.postForObject(backendURL + "users/", user, User.class);
    }*/
    public ResponseEntity create(User user) {
        HttpEntity<User> entity = new HttpEntity<>(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        ResponseEntity<User> response;
        try{
            response = restTemplate.postForEntity(backendURL + "users/" , entity , User.class);
        } catch (HttpClientErrorException.BadRequest ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    public void update(User user, Long id) {
        restTemplate.put(backendURL + "users/" + id, user);
    }

    public void delete(Long id) {
        restTemplate.delete(backendURL + "users/" + id);
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = getUserByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }
}

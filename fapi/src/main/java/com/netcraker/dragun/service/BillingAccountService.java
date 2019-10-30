package com.netcraker.dragun.service;

import com.netcraker.dragun.model.BillingAccount;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class BillingAccountService {
    private RestTemplate restTemplate;
    @Value("${backend.url}")
    private String backendURL;
    public BillingAccountService(RestTemplateBuilder builder){
        this.restTemplate = builder.build();
    }
    public BillingAccount get(Long id) {
        return restTemplate.getForObject(backendURL+"billingaccount/"+id, BillingAccount.class);
    }
    public List<BillingAccount> getAll() {
        return Arrays.asList(restTemplate.getForObject(backendURL+"billingaccount/", BillingAccount[].class));
    }
    public BillingAccount create(BillingAccount billingAccount) {
        return restTemplate.postForObject(backendURL+"billingaccount/", billingAccount, BillingAccount.class);
    }

    public void update(BillingAccount billingAccount, Long id) {
        restTemplate.put(backendURL+"billingaccount/"+id, billingAccount);
    }

    public void delete(Long id) {
        restTemplate.delete(backendURL+"billingaccount/"+id);
    }
}
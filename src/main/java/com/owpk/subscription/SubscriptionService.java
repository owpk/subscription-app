package com.owpk.subscription;

import java.util.List;

import org.springframework.stereotype.Service;

import com.owpk.subscription.jpa.Subscription;
import com.owpk.subscription.jpa.SubscriptionRepository;

@Service
public class SubscriptionService {
    private final SubscriptionRepository repository;
    private final JwtService jwtService;

    public SubscriptionService(SubscriptionRepository repository, JwtService jwtService) {
        this.repository = repository;
        this.jwtService = jwtService;
    }

    public List<Subscription> listAll(String filterId) {
        return (filterId == null || filterId.isBlank())
                ? repository.findAll()
                : repository.findAll().stream()
                        .filter(sub -> sub.getId().toLowerCase().contains(filterId.toLowerCase()))
                        .toList();
    }

    public Subscription create(String id) {
        var sub = new Subscription();
        var jwt = jwtService.generateToken(id);
        sub.setId(id);
        sub.setKey(jwt.jwt());
        sub.setExpirationDate(jwt.expiresAt());
        return repository.save(sub);
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}
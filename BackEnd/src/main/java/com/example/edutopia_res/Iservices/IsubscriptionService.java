package com.example.edutopia_res.Iservices;


import com.example.edutopia_res.entities.Subscription;
import com.example.edutopia_res.entities.User;
import com.google.zxing.WriterException;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface IsubscriptionService {
    void deleteSubscription(int id);
    Subscription createSubscription(Subscription subscription);
    Subscription getSubscriptionById(int id);
    List<Subscription> getAllSubscriptions();
    Subscription updateSubscription(Subscription subscription);

    Subscription purchasePackage(int userId, Subscription.PackageType packageType  , Date endDate ) throws IOException, WriterException;
    void generateQRCode(User user, Subscription subscription) throws IOException, WriterException ;
}

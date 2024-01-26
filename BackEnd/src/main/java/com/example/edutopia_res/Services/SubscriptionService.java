package com.example.edutopia_res.Services;


import com.example.edutopia_res.Iservices.IsubscriptionService;
import com.example.edutopia_res.Repository.MenuRepository;
import com.example.edutopia_res.Repository.SubscriptionRepository;
import com.example.edutopia_res.Repository.UserRepository;
import com.example.edutopia_res.entities.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.ws.rs.NotFoundException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service

public class SubscriptionService implements IsubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MenuRepository menuRepository;
    private String phoneNumber ="ssqdqsd";
    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public Subscription getSubscriptionById(int id) {
        return subscriptionRepository.findById(id).orElse(null);
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription updateSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public void deleteSubscription(int id) {
        subscriptionRepository.deleteById(id);
    }


    public  Subscription purchasePackage(int userId, Subscription.PackageType packageType  ,Date endDate ) throws IOException, WriterException {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user id: " + userId));


        Subscription subscription = new Subscription();
        subscription.setCreatedAt(new Date());
        subscription.setPackageType(packageType);

        subscription.setStartDate(subscription.getCreatedAt());
        subscription.setEndDate(endDate);

        long durationInMillis =  subscription.getEndDate().getTime() - subscription.getStartDate().getTime();
        int durationInDays = (int) TimeUnit.DAYS.convert(durationInMillis, TimeUnit.MILLISECONDS);
        subscription.setDuration(durationInDays);

        switch (packageType) {
            case FOODIE:
                subscription.setPerks("Access to exclusive foodie events, Monthly foodie newsletter");
                break;
            case BIG_FOODIE:
                subscription.setPerks("Access to exclusive foodie events, Monthly foodie newsletter, 10% off on all partner restaurants");
                break;
            case ULTRA_FOODIE:
                subscription.setPerks("Access to exclusive foodie events, Monthly foodie newsletter ,20% off on all menus, freebies");
                break;
        }

        LocalDate start = subscription.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = subscription.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long days = ChronoUnit.DAYS.between(start, end);

        // Set the price based on the package type
        BigDecimal price = null;
        switch (packageType) {
            case FOODIE:
                price = new BigDecimal("10.00");

                break;
            case BIG_FOODIE:
                price = new BigDecimal("20.00");
                break;
            case ULTRA_FOODIE:
                price = new BigDecimal("30.00");
                break;
        }
        if (days > 30) {
            int numMonths = (int) Math.ceil(days / 30.0);
            price = price.multiply(new BigDecimal(numMonths));
        }
        subscription.setPrice(price);

        // Set the user on the subscription entity
        subscription.setUser(user);

        // Save the Subscription
        subscriptionRepository.save(subscription);

        // Generate QR code
        generateQRCode(user, subscription);


        return subscription;
    }

    public void generateQRCode(User user, Subscription subscription) throws IOException, WriterException {
        String qrCodeData = String.format("User ID: %d, Subscription ID: %d, Package Type: %s", user.getUserId(), subscription.getSubscriptionId(), subscription.getPackageType());
        String filePath = String.format("%d_%d.png", user.getUserId(), subscription.getSubscriptionId(),subscription.getPackageType());
        int size = 256;
        String fileType = "png";
        File qrFile = new File(filePath);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeData, BarcodeFormat.QR_CODE, size, size);
        MatrixToImageWriter.writeToFile(bitMatrix, fileType, qrFile);
        System.out.println("QR code image file generated and saved at: " + filePath);
    }

    /*@Scheduled(cron = "0 * * ? * *")*/
    public void notifSms(){
        List<Subscription>subscriptions=subscriptionRepository.findAll();
        List<Menu>menus=menuRepository.findAll();
        Date curDate = new Date();
        System.out.println("***********"+curDate);
        for(Subscription s : subscriptions){
            String favDish=s.getPreferredDish();
            User user =s.getUser();
            for (Menu m: menus){
                int diffDays=(int)(curDate.getTime()-m.getCreatedAt().getTime()) / (1000 * 60 * 60 * 24);
                System.out.println(diffDays);
                if(diffDays==0) {
                    for (Dish d : m.getDishes()) {
                        if (d.getName().equals(favDish)) {
                                    AlertMessage alertMessage=new AlertMessage("your favorite dish  "+d.getName()+" in the restaurant today welcome back !");
                                Message.creator(new PhoneNumber(user.getPhoneNumber()),
                                new PhoneNumber(phoneNumber),
                             alertMessage.getMessage()).create();
                        }
                    }
                }}}}}



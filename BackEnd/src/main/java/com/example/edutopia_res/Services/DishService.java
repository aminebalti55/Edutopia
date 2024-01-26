package com.example.edutopia_res.Services;


import com.example.edutopia_res.Iservices.IDishService;
import com.example.edutopia_res.Iservices.IuserService;
import com.example.edutopia_res.Repository.DishRepository;
import com.example.edutopia_res.Repository.RatingsRepository;
import com.example.edutopia_res.Repository.ReportRepository;
import com.example.edutopia_res.Repository.UserRepository;
import com.example.edutopia_res.entities.*;
import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service

public class DishService implements IDishService {


    @Autowired
    DishRepository dishRepository;
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    RatingsRepository ratingsRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    IuserService userservice;
    public Dish createDish(String name, String description, float price, MultipartFile image ,Category category, SpicinessLevel spicinessLevel) throws IOException {
        Dish dish = new Dish();
        dish.setName(name);
        dish.setDescription(description);
        dish.setPrice(price);
        dish.setCategory(category);
        dish.setSpicinessLevel(spicinessLevel);

        System.out.println("name: " + name);
        System.out.println("description: " + description);
        System.out.println("price: " + price);
        System.out.println("image: " + image);

        if (image != null && !image.isEmpty()) {
            String fileName = image.getOriginalFilename();
            Path filePath = Paths.get("C:/Users/user/Desktop/spring boot/Edutopia_res/Path/to/images", fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            dish.setImage(filePath.toString());


        }

        return dishRepository.save(dish);
    }


    /*public Dish updateDish(int id, Dish dish, MultipartFile image) throws IOException {

    }*/

    public void deleteDish(int id) {
        dishRepository.deleteById(id);
    }






    public List<Dish> getAllDishes() {
        List<Dish> dishes = dishRepository.findAll();

        for (Dish dish : dishes) {
            Dish dishDto = new Dish();
            dishDto.setDishId(dish.getDishId());
            dishDto.setName(dish.getName());
            dishDto.setDescription(dish.getDescription());
            dishDto.setPrice(dish.getPrice());
            String image_path = dish.getImage();

            if (image_path != null) {
                Path filePath = Paths.get(image_path);
                byte[] imageData = null;
                try {
                    imageData = Files.readAllBytes(filePath);
                } catch (IOException e) {
                    // handle the exception appropriately
                }
                dish.setDishimageData(imageData);
            }


    }   return dishes;

    }



    public Optional<Dish> getDishById(int id) {
        return dishRepository.findById(id);
    }

    public Dish updateDish(int id, String name, String description, float price, MultipartFile image, Category category, SpicinessLevel spicinessLevel) throws IOException {
        Optional<Dish> optionalDish = dishRepository.findById(id);
        if (!optionalDish.isPresent()) {
            throw new IllegalArgumentException("Dish with id " + id + " does not exist");
        }

        Dish dish = optionalDish.get();

        if (name != null) {
            dish.setName(name);
        }

        if (description != null) {
            dish.setDescription(description);
        }

        if (price >= 0) {
            dish.setPrice(price);
        }

        if (category != null) {
            dish.setCategory(category);
        }

        if (spicinessLevel != null) {
            dish.setSpicinessLevel(spicinessLevel);
        }

        if (image != null && !image.isEmpty()) {
            String originalFilename = image.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            if (!Arrays.asList("jpg", "jpeg", "png", "gif").contains(extension.toLowerCase())) {
                throw new IllegalArgumentException("File must be a valid image (JPG, JPEG, PNG, GIF)");
            }

            String uniqueFilename = UUID.randomUUID().toString() + "." + extension;
            Path filePath = Paths.get("C:/Users/user/Desktop/spring boot/Edutopia_res/Path/to/images", uniqueFilename);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            dish.setImage(filePath.toString());
        }

        return dishRepository.save(dish);
    }
    public List<Dish> getTop3HighestRatedDishes() throws Exception {
        List<Dish> top3Dishes = dishRepository.findTop3ByOrderByScoreDesc();
        for (Dish dish : top3Dishes) {
            String imagePath = dish.getImage();
            String base64Image = ImageUtils.encodeImageToBase64(imagePath);
            dish.setImage(base64Image);
        }
        return top3Dishes;
    }

    public Dish getHighestRatedDish() {
        // Retrieve the dish with the highest score
        return dishRepository.findTopByOrderByScoreDesc();
    }
    public String generateRssFeed(Dish bestRatedDish) {
        // Create a new SyndFeed object
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0");

        // Set the title and description of the feed
        feed.setTitle("Canteen Best Rated Dish");
        feed.setDescription("The best rated dish from the canteen.");
        feed.setLink("http://localhost:8086/Edutopia");

        // Create a new SyndEntry object for the best-rated dish
        SyndEntry entry = new SyndEntryImpl();
        entry.setTitle(bestRatedDish.getName());
        entry.setLink("http://localhost:8086/Edutopia/dishes/" + bestRatedDish.getDishId());
       SyndContent description = new SyndContentImpl();

       description.setValue(bestRatedDish.getDescription());
       entry.setDescription(description);

        // Create a new SyndContent object for the image of the best-rated dish
        SyndContent imageContent = new SyndContentImpl();
        imageContent.setType("image/jpeg");
        imageContent.setValue(bestRatedDish.getImage());

        // Add the image content to the SyndEntry object
        entry.setContents(Collections.singletonList(imageContent));

        // Add the SyndEntry object to the feed
        feed.setEntries(Collections.singletonList(entry));

        // Generate the RSS feed as a string
        StringWriter writer = new StringWriter();
        SyndFeedOutput output = new SyndFeedOutput();
        try {
            output.output(feed, writer);
        } catch (IOException | FeedException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }

    public void sendRssNewsletter() {
        // Retrieve the highest-rated dish
        Dish bestRatedDish = getHighestRatedDish();
        List<User> subscribedUsers = userservice.getSubscribedUsers();

        // Generate the RSS feed that includes the details of the best-rated dish
        String rssFeed = generateRssFeed(bestRatedDish);

        for (User user : subscribedUsers) {
            if (user.isSubscribedToRss()) {

        String host = "smtp.gmail.com";
        int port = 587;
        String username = "ccandyxx1@gmail.com";
        String password = "rmddmtsaxxeqplwe";
                String recipient = user.getEmail();
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Canteen Best Rated Dish");
            message.setContent(rssFeed, "text/html; charset=utf-8");
            Transport.send(message);
            System.out.println("RSS newsletter sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
            }
        }
    }

    @Scheduled(cron = "0 0 12 * * 1") // run at noon every Monday
    public void sendRssNewsletterWeekly() {
        sendRssNewsletter();
    }


   /* public List<Dish> getDishesByCategory(Category category) {
        if (category == null) {
            return dishRepository.findAll();
        } else {
            return dishRepository.findByCategory(category);
        }
    }*/

    public List<Dish> getRecommendedDishes(String username) throws Exception {
        User user = userRepository.findByUsername(username);
        System.out.println("User: " + user);

        SpicinessLevel userSpicinessTolerance = user.getSpicinessTolerance();
        List<Dish> allDishes = dishRepository.findAll();
        System.out.println("All dishes: " + allDishes);

        List<Dish> recommendedDishes = new ArrayList<>();

        for (Dish dish : allDishes) {
            if (dish.getSpicinessLevel().ordinal() <= userSpicinessTolerance.ordinal()) {
                String imagePath = dish.getImage();
                String base64Image = ImageUtils.encodeImageToBase64(imagePath);
                dish.setImage(base64Image);
                recommendedDishes.add(dish);
            }
        }

        System.out.println("Recommended dishes: " + recommendedDishes);
        return recommendedDishes;
    }


}





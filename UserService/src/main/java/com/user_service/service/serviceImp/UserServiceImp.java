package com.user_service.service.serviceImp;


import com.user_service.entity.Hotel;
import com.user_service.entity.Rating;
import com.user_service.entity.User;
import com.user_service.exception.ResourceNotFoundException;
import com.user_service.repository.UserRepository;
import com.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public User createUser(User user) {

        return userRepository.save(user);
    }

    @Override
    public User getUserById(int userId) {
        User user = userRepository.findById(String.valueOf(userId)).orElseThrow(() -> new ResourceNotFoundException("id not found " + userId));
        //ArrayList<Rating> ratingList = restTemplate.getForObject("http://localhost:8085/rating/user/1", ArrayList.class);
        Rating[] ratingList = restTemplate.getForObject("http://RATING-SERVICE/rating/user/" + user.getUserId(), Rating[].class);
       //instead of localhost:8085 we can write application name which we have set in application.property and have regostered with eureka also
        //we can check this name in localhost:8761 (eureka server port ) where we have registered our services
        //we have to tell rest template do not take port you must take service name add @Loadbalance on method where we have    declared bean of it
    /*using for loop
     for(int i=0;i<ratingList.length;i++)
        {

            int hotelId=ratingList[i].getHotelId();
            Hotel hotel = restTemplate.getForObject("http://localhost:8084/hotel/"+hotelId,Hotel.class);
            ratingList[i].setHotel(hotel);
        }*/

        //using stream
        List<Rating> ratings = Arrays.stream(ratingList).toList();
      List<Rating>  ratingWithHotel=  ratings.stream().map(rating -> {
            Hotel hotel = restTemplate.getForObject("http://HOTEL-SERVICE/hotel/" + rating.getHotelId(), Hotel.class);
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());
        System.out.println(ratingList);
        user.setList(ratingWithHotel);
        return user;
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(int userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(String.valueOf(userId)).orElseThrow(() -> new ResourceNotFoundException(" Resource not found" + userId)));
        userRepository.delete(user.get());
    }

    @Override
    public User updateUser(int userId, User user) {
        Optional<User> originalUser = userRepository.findById(String.valueOf(userId));
        User updatedUser = originalUser.get();
        updatedUser.setName(user.getName());
        updatedUser.setEmail(user.getEmail());
        return userRepository.save(updatedUser);
    }
}

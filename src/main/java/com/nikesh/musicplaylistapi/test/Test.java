package com.nikesh.musicplaylistapi.test;

import com.nikesh.musicplaylistapi.dto.request.UserRequestDTO;
import com.nikesh.musicplaylistapi.entities.User;
import org.modelmapper.ModelMapper;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
public class Test {

    public static void main(String[] args) {
        User user = new User();
        user.setId(1L);
        user.setPassword("slsdkfjdsljf dsflkdsj flsdjfdsfkdsjf dsfds");
        user.setUsername("nikeshmhr");

        System.out.println("USER :: " + user);

        ModelMapper map = new ModelMapper();
        UserRequestDTO map1 = map.map(user, UserRequestDTO.class);

        System.out.println("USER DTO :: " + map1);
    }

}

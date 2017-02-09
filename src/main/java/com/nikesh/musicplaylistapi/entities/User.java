package com.nikesh.musicplaylistapi.entities;

import com.nikesh.musicplaylistapi.core.ModelBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Nikesh Maharjan
 *         nikeshmhr@gmail.com
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends ModelBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String username;

    @Column(length = 50)
    private String password;

}

package com.travel.web.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "passengers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email",  nullable = false)
    private String email;

    @Column(name = "phone_number",  nullable = false)
    private String phoneNumber;

    
    @Column(name = "is_seat_allotted")
    private boolean isSeatAllotted = false;

    @Column(name = "gender", nullable = false)
    private String gender;  // Can be "Male", "Female", "Other"

    @Column(name = "age", nullable = false)
    private int age;
    
    
}

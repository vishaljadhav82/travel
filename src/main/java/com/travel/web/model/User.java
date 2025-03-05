package com.travel.web.model;

import java.util.List;

import com.travel.web.config.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username; // Unique username for the user

    @Column(name = "password", nullable = false)
    private String password; // User password

    @Column(name = "email", unique = true, nullable = false)
    private String email; // User email address

    @Column(name = "phone_number", unique = true, nullable = false)
    private String phoneNumber; // User phone number

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role; // User role (USER or ADMIN)

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Passenger> passengers; // List of passengers associated with the user
    
    private List<Long> booking;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}

	public List<Long> getBooking() {
		return booking;
	}

	public void setBooking(List<Long> booking) {
		this.booking = booking;
	}
    
   
}

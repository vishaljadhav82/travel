package com.travel.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.travel.web.model.Farmer;

public interface FarmerRepository extends JpaRepository<Farmer, Long> {
    Farmer findByEmailAndMobileNumber(String email, String mobileNumber);
    Farmer findByEmail(String email);
}

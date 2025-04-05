package com.travel.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.travel.web.model.Stop;

public interface StopRepository extends JpaRepository<Stop, Long> {
    List<Stop> findByRouteId(Long routeId);
    @Query("SELECT s FROM Stop s WHERE " +
    	       "(:distance IS NULL OR s.distanceFromStart = :distance) AND " +
    	       "(:stopTime IS NULL OR s.stopTime = :stopTime) AND " +
    	       "(:routeName IS NULL OR LOWER(s.route.routeName) LIKE LOWER(CONCAT('%', :routeName, '%')))")
    	Page<Stop> findByFilters(@Param("distance") Integer distance,
    	                         @Param("stopTime") Integer stopTime,
    	                         @Param("routeName") String routeName,
    	                         Pageable pageable);


}


package com.travel.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travel.web.model.Bus;
import com.travel.web.model.Seat;
import com.travel.web.repository.BusRepository;
import com.travel.web.repository.SeatRepository;

@Controller
@RequestMapping("/admin/buses")
public class BusController {

    @Autowired
    private BusRepository busRepository;
    @Autowired
    private SeatRepository seatRepository;

    @GetMapping
    public String listBuses(Model model) {
        List<Bus> buses = busRepository.findAll();
        model.addAttribute("buses", buses);
        return "bus/bus-list";
    }

    @GetMapping("/new")
    public String createBusForm(Model model) {
        model.addAttribute("bus", new Bus());
        return "bus/bus-form";
    }

    @PostMapping
    public String saveBus(@ModelAttribute Bus bus) {
        int capacity = bus.getCapacity();  // use capacity, not totalSeats

        bus.setAvailable(true);

        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i <= capacity; i++) {
            Seat seat = new Seat();
            seat.setSeatNumber(i);
            seat.setBus(bus);  // <--- very important: link seat to bus
            seats.add(seat);
        }

        bus.setBusSeats(seats);

        busRepository.save(bus);  // will save bus + all seats
        return "redirect:/admin/buses";
    }

}
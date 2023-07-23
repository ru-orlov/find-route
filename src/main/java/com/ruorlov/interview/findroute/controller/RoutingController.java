package com.ruorlov.interview.findroute.controller;

import com.ruorlov.interview.findroute.service.RoutingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("routing")
public class RoutingController  {

    private final RoutingService routingService;

    public RoutingController(RoutingService routingService) {
        this.routingService = routingService;
    }

    @GetMapping("/{origin}/{destination}")
    public List<String> getPath(@PathVariable String origin, @PathVariable String destination) {
        return routingService.route(origin, destination);
    }

}

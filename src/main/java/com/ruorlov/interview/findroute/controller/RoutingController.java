package com.ruorlov.interview.findroute.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("routing")
public class RoutingController  {
    @GetMapping("/{origin}/{destination}")
    public String getPath(@PathVariable String origin, @PathVariable String destination) {
        return "path: " + origin + " -> " + destination;
    }

}

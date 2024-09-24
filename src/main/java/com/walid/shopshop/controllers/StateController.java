package com.walid.shopshop.controllers;

import com.walid.shopshop.entities.Country;
import com.walid.shopshop.entities.State;
import com.walid.shopshop.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1/states")
public class StateController {

    @Autowired
    StateService stateService;


    @GetMapping("/countries")
    public List<Country> getCountries(){
        return stateService.getAllCountries();
    }
    @GetMapping("/country/{name}")
    public List<State> getStates(@PathVariable String name){
        return stateService.findByCountryName(name);
    }
}

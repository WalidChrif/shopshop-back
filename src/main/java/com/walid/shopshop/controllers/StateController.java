package com.walid.shopshop.controllers;

import com.walid.shopshop.entities.Country;
import com.walid.shopshop.entities.State;
import com.walid.shopshop.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/states")
public class StateController {

    @Autowired
    StateService stateService;


    @GetMapping("/countries")
    public List<Country> getCountries(){
        return stateService.getAllCountries();
    }
    @GetMapping("/country/{id}")
    public List<State> getStates(@PathVariable Long id){
        return stateService.findByCountryId(id);
    }
}

package com.walid.shopshop.services;

import com.walid.shopshop.entities.Country;
import com.walid.shopshop.entities.State;
import com.walid.shopshop.repos.CountryRepo;
import com.walid.shopshop.repos.StateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    StateRepo stateRepo;
    @Autowired
    CountryRepo countryRepo;

    @Override
    public List<Country> getAllCountries() {
        return countryRepo.findAll();
    }

    @Override
    public List<State> findByCountryName(String name) {
        return stateRepo.findByCountryName(name);
    }
}

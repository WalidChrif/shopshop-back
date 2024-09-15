package com.walid.shopshop.services;

import com.walid.shopshop.entities.Country;
import com.walid.shopshop.entities.State;

import java.util.List;

public interface StateService {

    List<Country> getAllCountries();
    List<State> findByCountryName(String name);


}

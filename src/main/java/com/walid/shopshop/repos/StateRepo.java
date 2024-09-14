package com.walid.shopshop.repos;

import com.walid.shopshop.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepo extends JpaRepository<State, Long> {
    List<State> findByCountryId(Long id);
}

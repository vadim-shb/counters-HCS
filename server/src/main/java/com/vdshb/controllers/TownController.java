package com.vdshb.controllers;

import com.vdshb.domain.Town;
import com.vdshb.repository.TownRepository;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;

@RestController
public class TownController {

    @Inject
    private TownRepository townRepository;

    //    @GetMapping("/api/town/{townId}")
//    public Town getTown(@PathVariable Long townId) {
//        return townRepository.findOne(townId);
//    }
//

    @GetMapping("/api/towns")
    public List<Town> getTowns() {
        List<Town> towns = IteratorUtils.toList(townRepository.findAll().iterator());
        towns.sort(Comparator.comparing(Town::getName));
        return towns;
    }

    @PostMapping("/api/town")
    @PreAuthorize("hasRole('ADMIN')")
    public Town createTown(@RequestBody Town town) {
        return townRepository.save(town);
    }

    @PutMapping("/api/town/{townId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Town> updateTown(@PathVariable Long townId, @RequestBody Town town) {
        Town persistedTown = townRepository.findOne(townId);
        if (persistedTown == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        persistedTown.setBeanPropertiesFromRestUpdate(town);
        townRepository.save(persistedTown);
        return ResponseEntity.ok(persistedTown);
    }

    @DeleteMapping("/api/town/{townId}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTown(@PathVariable Long townId) {
        townRepository.delete(townId);
    }

}

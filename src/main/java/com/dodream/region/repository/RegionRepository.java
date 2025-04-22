package com.dodream.region.repository;

import com.dodream.region.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByRegionName(String regionName);
    Optional<Region> findByRegionCode(String regionCode);
}

package com.dodream.region.repository;

import com.dodream.region.domain.Region;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    Optional<Region> findByRegionName(String regionName);
    Optional<Region> findByRegionCode(String regionCode);
}

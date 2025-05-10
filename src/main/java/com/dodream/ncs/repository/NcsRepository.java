package com.dodream.ncs.repository;

import com.dodream.ncs.domain.Ncs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NcsRepository extends JpaRepository<Ncs, Integer> {

    Optional<Ncs> findByNcsName(String ncsName);

    Optional<Ncs> findByNcsCode(String ncsCode);
}

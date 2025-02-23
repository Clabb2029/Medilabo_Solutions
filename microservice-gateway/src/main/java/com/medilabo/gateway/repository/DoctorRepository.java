package com.medilabo.gateway.repository;

import com.medilabo.gateway.model.Doctor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface DoctorRepository extends ReactiveCrudRepository<Doctor, Integer> {

    Mono<Doctor> findByUsername(String username);
}

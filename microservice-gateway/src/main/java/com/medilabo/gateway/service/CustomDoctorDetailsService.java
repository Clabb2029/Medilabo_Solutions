package com.medilabo.gateway.service;

import com.medilabo.gateway.repository.DoctorRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomDoctorDetailsService implements ReactiveUserDetailsService {

    private DoctorRepository doctorRepository;

    public CustomDoctorDetailsService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return doctorRepository.findByUsername(username)
                .map(doctor ->
                        User
                                .withUsername(doctor.getUsername())
                                .password(doctor.getPassword())
                                .build()
                )
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("Doctor not found")));
    }

}

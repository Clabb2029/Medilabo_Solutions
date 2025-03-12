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

    /**
     * Finds a doctor by their username and returns the corresponding {@link UserDetails} object.
     * If the doctor is not found, an error is returned.
     *
     * @param username The username of the doctor to be found.
     * @return A {@link Mono} containing the {@link UserDetails} object representing the doctor, or an error if the doctor is not found.
     * @throws UsernameNotFoundException if the doctor with the given username is not found in the repository.
     */
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return doctorRepository.findByUsername(username)
                .map(doctor ->
                        User
                                .withUsername(doctor.getUsername())
                                .password(doctor.getPassword())
                                .build()
                )
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("Docteur non trouvé")));
    }

}

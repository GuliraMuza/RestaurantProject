package peaksoft.service;

import org.springframework.stereotype.Repository;
import peaksoft.dto.register.AuthenticationResponse;
import peaksoft.dto.register.reguest.SignInRequest;
import peaksoft.dto.register.reguest.SignUpRequest;

@Repository
public interface AuthenticationService {
   // AuthenticationResponse signUp(SignUpRequest request);

    AuthenticationResponse signIn(SignInRequest request);
}

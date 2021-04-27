package main.springsecurity;

import main.model.Users;
import main.model.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {

  private UserRepository userRepository;

  public UserPrincipalDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

    Users user = userRepository.findByName(userName);
    if (user == null){
      throw new UsernameNotFoundException(userName);
    }
    return new UserPrincipal(user);
  }
}

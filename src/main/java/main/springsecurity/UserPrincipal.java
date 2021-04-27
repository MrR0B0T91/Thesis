package main.springsecurity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import main.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

  private Users user;

  public UserPrincipal(Users user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();
    if (user.isModerator()) {
      GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_MODERATOR");
      authorities.add(authority);
    }
    return authorities;
  }

  @Override
  public String getPassword() {
    return this.user.getPassword();
  }

  @Override
  public String getUsername() {
    return this.user.getName();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public boolean isModerator() {
    return this.user.isModerator();
  }

  public int getId() {
    return this.user.getId();
  }

  public String getPhoto() {
    return this.user.getPhoto();
  }

  public String getEmail() {
    return this.user.getEmail();
  }
}

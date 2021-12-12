package me.imatveev.thechat.security.service.impl;

import lombok.RequiredArgsConstructor;
import me.imatveev.thechat.domain.service.UserService;
import me.imatveev.thechat.security.service.UserSecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService {
    private final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        return userService.findByPhone(phone)
                .orElseThrow(() -> new UsernameNotFoundException("Not found user with phone - " + phone));
    }
}

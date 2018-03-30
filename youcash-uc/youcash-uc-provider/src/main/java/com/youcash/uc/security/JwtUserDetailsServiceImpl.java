package com.youcash.uc.security;

import com.youcash.uc.entity.User;
import com.youcash.uc.entity.UserExample;
import com.youcash.uc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author linhansheng.
 * @date 2018/3/23
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        if (users == null || users.size() == 0) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            User user = users.get(0);
            List<String> roles = new ArrayList<>();
            roles.add("ROLE_USER");
            return new JwtUser(user.getUserName(), user.getUserPassword(), roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
    }
}

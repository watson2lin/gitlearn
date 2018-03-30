package com.youcash.uc.service.impl.emp;

import com.youcash.uc.entity.User;
import com.youcash.uc.entity.UserExample;
import com.youcash.uc.mapper.UserMapper;
import com.youcash.uc.security.JwtTokenUtil;
import com.youcash.uc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author linhansheng.
 * @date 2018/3/23
 */
@Service("userService")
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService{
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userMapper = userMapper;
    }

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    @Override
    public String register(User user) {
        String username = user.getUserName();
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        if (users != null && users.size() != 0) {
            return "用户已存在";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = user.getUserPassword();
        user.setUserPassword(encoder.encode(rawPassword));
        user.setUserId(String.valueOf(new Random().nextInt()));
        List<String> roles = new ArrayList<>();

        userMapper.insert(user);
        return "success";
    }

    @Override
    public String refreshToken(String oldToken) {
        if(StringUtils.isEmpty(oldToken)){
            return "error:oldToken is null";
        }
        String token = oldToken.substring("Bearer ".length());
        if (!jwtTokenUtil.isTokenExpired(token)) {
            return jwtTokenUtil.refreshToken(token);
        }
        return "error";
    }
}

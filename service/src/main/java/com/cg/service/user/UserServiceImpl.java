package com.cg.service.user;

import com.cg.domain.dto.UserDTO;
import com.cg.domain.entity.User;
import com.cg.domain.entity.UserPrinciple;
import com.cg.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserServiceImpl implements IUserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return null;
    }

    //1.Hiển thị tất cả danh sách User
    @Override
    public List<UserDTO> findAllUserDTOByDeletedIsFalse() {
        return userRepository.findAllUserDTOByDeletedIsFalse();
    }

    @Override
    public List<UserDTO> findAllUserDTOByDeletedIsFalseRoleAdmin(String admin) {
        return userRepository.findAllUserDTOByDeletedIsFalseRoleAdmin(admin);
    }

    @Override
    public List<UserDTO> findAllUserDTOByDeletedIsFalseRoleStaff(String staff) {
        return userRepository.findAllUserDTOByDeletedIsFalseRoleStaff(staff);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id.toString());
    }

    @Override
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public User save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword())); //mã hóa mật khẩu

        return userRepository.save(user);

    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(username);
        }

        return UserPrinciple.build(userOptional.get());
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

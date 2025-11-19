package com.example.retosflags.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.retosflags.dto.RetoDTO;
import com.example.retosflags.dto.UserDTO;
import com.example.retosflags.mapper.RetoMapper;
import com.example.retosflags.mapper.UserMapper;
import com.example.retosflags.model.User;
import com.example.retosflags.repository.RetoRepository;
import com.example.retosflags.repository.UserRepository;
import com.example.retosflags.model.Reto;;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RetoRepository retoRepository;
    @Autowired
    private RetoMapper retoMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void addUser(User user) {
        User guardar=new User(user.getUsername(), passwordEncoder.encode(user.getPassword()));
        userRepository.save(guardar);
    }

    public User logUser(User user) {
        Optional<User> found = userRepository.findByUsername(user.getUsername());
        if (found.isPresent()&&passwordEncoder.matches(user.getPassword(), found.get().getPassword())){
            return found.get();
        }else{
            return null;
        }
    }

    public User getUser(Long userId){
        return userRepository.findById(userId).orElse(null);
    }

    public void retoResuelto(RetoDTO reto, Long userId) {
        User user = getUser(userId);
        if (user != null && reto != null) {
            Reto resuelto = retoMapper.toDomain(reto);
            user.addRetoResuelto(resuelto);
            userRepository.save(user); 
        }
    }

    public List<RetoDTO> getRetosDTOResueltos(User user) {
        List<Reto> retos=user.getRetosResueltos();
        if(retos==null){
            return null;
        }else{
            return retoMapper.toDTOs(retos);
        }
    }

    public UserDTO toDTO(User user) {
        return userMapper.toDTO(user);
    }

    public void actualizarRetosUsuario(Long userId, RetoDTO reto) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Reto guardar = retoMapper.toDomain(reto);
            guardar.setUser(user);
            user.addRetoSubido(guardar);
            retoRepository.save(guardar);
            userRepository.save(user);
        }
    }
    public void updateUser(User user){
        userRepository.save(user);
    }
    public User updateUserWithRetoResuelto(String username, Reto reto) {
        User user = userRepository.findByUsernameWithRetosResueltos(username);
        if (user != null && reto != null) {
            user.addRetoResuelto(reto);
            return userRepository.save(user);
        }
        return null;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}

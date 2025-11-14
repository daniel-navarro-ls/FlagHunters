package com.example.retosflags.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.retosflags.dto.RetoDTO;
import com.example.retosflags.dto.UserDTO;
import com.example.retosflags.mapper.RetoMapper;
import com.example.retosflags.mapper.UserMapper;
import com.example.retosflags.model.User;
import com.example.retosflags.repository.UserRepository;
import com.example.retosflags.model.Reto;;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RetoMapper retoMapper;
    @Autowired
    private UserMapper userMapper;

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User logUser(User user) {
        Optional<User> found = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        return found.orElse(null);
    }

    public User getUser(Long userId){
        return userRepository.findById(userId).orElse(null);
    }

    public void retoResuelto(RetoDTO reto, Long userId) {
        User user=getUser(userId);
        Reto resuelto=retoMapper.toDomain(reto);
        user.addRetoResuelto(resuelto);
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
}

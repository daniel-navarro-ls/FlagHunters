package com.example.retosflags.mapper;

import com.example.retosflags.dto.UserAuthDTO;
import com.example.retosflags.dto.UserDTO;
import com.example.retosflags.model.User;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-25T16:01:47+0100",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.44.0.v20251118-1623, environment: Java 21.0.9 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        Long id = null;
        String username = null;

        id = user.getId();
        username = user.getUsername();

        UserDTO userDTO = new UserDTO( id, username );

        return userDTO;
    }

    @Override
    public User toDomain(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userDTO.username() );
        user.setId( userDTO.id() );

        return user;
    }

    @Override
    public List<UserDTO> toDTOs(Collection<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( users.size() );
        for ( User user : users ) {
            list.add( toDTO( user ) );
        }

        return list;
    }

    @Override
    public User toDomainAuth(UserAuthDTO userAuthDTO) {
        if ( userAuthDTO == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( userAuthDTO.username() );
        user.setPassword( userAuthDTO.password() );

        return user;
    }
}

package com.task.tracking.service.mapper;

import com.task.tracking.service.domain.dto.UserDto;
import com.task.tracking.service.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Interface maps entities to dtos and vice versa
 */
@Component
public interface UserMapper {

    UserDto entityToDto(User user);

    User dtoToEntity(UserDto dto);

    List<UserDto> entityListToDtoList(List<User> userList);

    List<User> dtoListToEntityList(List<UserDto> userDtos);

}

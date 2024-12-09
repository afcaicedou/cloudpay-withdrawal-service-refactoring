package org.caicedo.andres.user.repository.gateway;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.common.exceptions.UserNotFoundException;
import org.caicedo.andres.user.controller.mapper.UserMapper;
import org.caicedo.andres.user.controller.model.User;
import org.caicedo.andres.user.repository.UserRepository;
import org.caicedo.andres.user.repository.model.UserEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRepositoryGateway {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public User findById(Long id) {

    Optional<UserEntity> user = userRepository.findById(id);

    if (user.isPresent()) {

      log.info("User with id {} was found", id);

      return userMapper.toDomain(user.get());
    }

    log.info("User with id {} was not found", id);

    throw new UserNotFoundException("User does not exist");
  }

  public List<User> getUsers() {

    List<UserEntity> users = userRepository.findAll();

    if (users.isEmpty()) {

      log.info("No users found");

      throw new UserNotFoundException("There are no users");
    }

    log.info("Found {} users", users.size());

    return userMapper.toDomain(users);
  }
}

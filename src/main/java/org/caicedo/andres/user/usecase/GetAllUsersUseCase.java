package org.caicedo.andres.user.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.user.controller.model.GetAllUsersResponse;
import org.caicedo.andres.user.controller.model.User;
import org.caicedo.andres.user.repository.gateway.UserRepositoryGateway;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllUsersUseCase {

  private final UserRepositoryGateway userRepositoryGateway;

  public GetAllUsersResponse execute() {

    log.info("Retrieving users");

    List<User> users = userRepositoryGateway.getUsers();

    log.info("Users retrieved successfully!");

    return GetAllUsersResponse.builder().users(users).build();
  }
}

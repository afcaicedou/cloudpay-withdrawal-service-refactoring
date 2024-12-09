package org.caicedo.andres.user.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.user.controller.model.GetUserResponse;
import org.caicedo.andres.user.controller.model.User;
import org.caicedo.andres.user.repository.gateway.UserRepositoryGateway;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetUserUseCase {

  private final UserRepositoryGateway userRepositoryGateway;

  public GetUserResponse execute(Long id) {

    log.info("Retrieving user with id {}", id);

    User user = userRepositoryGateway.findById(id);

    log.info("User with id {} retrieved successfully!", id);

    return GetUserResponse.builder().user(user).build();
  }
}

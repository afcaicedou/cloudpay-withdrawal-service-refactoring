package org.caicedo.andres.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.user.controller.model.GetUserResponse;
import org.caicedo.andres.user.usecase.GetUserUseCase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class GetUserController {

  private final GetUserUseCase getUserUseCase;

  @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetUserResponse> findUser(@PathVariable Long userId) {
    return ResponseEntity.ok(getUserUseCase.execute(userId));
  }
}

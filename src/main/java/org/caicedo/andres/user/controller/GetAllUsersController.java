package org.caicedo.andres.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.caicedo.andres.user.controller.model.GetAllUsersResponse;
import org.caicedo.andres.user.usecase.GetAllUsersUseCase;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class GetAllUsersController {

  private final GetAllUsersUseCase getAllUsersUseCase;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GetAllUsersResponse> findAllUsers() {
    return ResponseEntity.ok(getAllUsersUseCase.execute());
  }
}

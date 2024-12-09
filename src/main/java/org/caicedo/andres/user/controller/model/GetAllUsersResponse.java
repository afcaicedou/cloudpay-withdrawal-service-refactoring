package org.caicedo.andres.user.controller.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAllUsersResponse {

  private List<User> users;
}

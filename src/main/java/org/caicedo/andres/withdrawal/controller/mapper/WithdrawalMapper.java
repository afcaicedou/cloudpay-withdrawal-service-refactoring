package org.caicedo.andres.withdrawal.controller.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import java.util.List;
import org.caicedo.andres.withdrawal.controller.model.Withdrawal;
import org.caicedo.andres.withdrawal.repository.model.WithdrawalEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface WithdrawalMapper {

  Withdrawal toDomain(WithdrawalEntity withdrawal);

  List<Withdrawal> toDomain(List<WithdrawalEntity> withdrawals);

  WithdrawalEntity toEntity(Withdrawal withdrawal);
}

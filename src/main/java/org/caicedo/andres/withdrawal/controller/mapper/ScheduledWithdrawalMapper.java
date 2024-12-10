package org.caicedo.andres.withdrawal.controller.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import java.util.List;
import org.caicedo.andres.withdrawal.controller.model.ScheduledWithdrawal;
import org.caicedo.andres.withdrawal.repository.model.ScheduledWithdrawalEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface ScheduledWithdrawalMapper {

  ScheduledWithdrawal toDomain(ScheduledWithdrawalEntity scheduledWithdrawal);

  List<ScheduledWithdrawal> toDomain(List<ScheduledWithdrawalEntity> scheduledWithdrawals);

  ScheduledWithdrawalEntity toEntity(ScheduledWithdrawal scheduledWithdrawal);
}

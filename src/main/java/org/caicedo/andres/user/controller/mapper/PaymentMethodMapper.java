package org.caicedo.andres.user.controller.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import org.caicedo.andres.user.controller.model.PaymentMethod;
import org.caicedo.andres.user.repository.model.PaymentMethodEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface PaymentMethodMapper {

  PaymentMethod toDomain(PaymentMethodEntity paymentMethod);
}

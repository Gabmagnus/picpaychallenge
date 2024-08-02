package com.gabma.picpay_challenge.dtos;

import com.gabma.picpay_challenge.domain.user.UserType;
import jakarta.persistence.UniqueConstraint;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

public record UserDTO(String firstName,
                      String lastName,
                      String document,
                      UserType userType,
                      BigDecimal balance,
                      String email,
                      String password ) {
}

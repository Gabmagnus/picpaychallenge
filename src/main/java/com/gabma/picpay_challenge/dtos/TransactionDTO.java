package com.gabma.picpay_challenge.dtos;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value,
                             Long senderId,
                             Long reciverId) {
}

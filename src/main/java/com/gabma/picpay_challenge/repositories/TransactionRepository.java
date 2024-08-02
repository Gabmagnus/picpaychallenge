package com.gabma.picpay_challenge.repositories;

import com.gabma.picpay_challenge.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}

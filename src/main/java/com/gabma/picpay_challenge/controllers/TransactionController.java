package com.gabma.picpay_challenge.controllers;

import com.gabma.picpay_challenge.domain.transaction.Transaction;
import com.gabma.picpay_challenge.dtos.TransactionDTO;
import com.gabma.picpay_challenge.repositories.TransactionRepository;
import com.gabma.picpay_challenge.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transactions")

public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> CreateNewTransaction(@RequestBody TransactionDTO data) throws Exception {
        Transaction newTransaction = this.transactionService.createTransaction(data);

        return new ResponseEntity<>(newTransaction, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List <Transaction>> GetAllTransactions() {
        return this.transactionService.getAllTransactions();
    }

}

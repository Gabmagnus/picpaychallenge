package com.gabma.picpay_challenge.services;

import com.gabma.picpay_challenge.domain.transaction.Transaction;
import com.gabma.picpay_challenge.domain.user.User;
import com.gabma.picpay_challenge.domain.user.UserType;
import com.gabma.picpay_challenge.repositories.TransactionRepository;
import com.gabma.picpay_challenge.dtos.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderId());
        User reciver = this.userService.findUserById(transaction.reciverId());


        // criando e setando valores da transacao
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReciver(reciver);
        newTransaction.setTimestamp(LocalDateTime.now());


        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        reciver.setBalance(reciver.getBalance().add(transaction.value()));

        // Validacoes
        authTransaction(newTransaction.getSender(), newTransaction.getAmount());
        validateTransaction(newTransaction.getSender(), newTransaction.getAmount());

        // Salvando transacao no banco
        transactionRepository.save(newTransaction);
        userService.saveUser(sender);
        userService.saveUser(sender);

        this.notificationService.sendNotification(sender, "Transacao realizada com sucesso");
        this.notificationService.sendNotification(reciver, "Transacao recebida com sucesso");

        return newTransaction;
    }

    public ResponseEntity<List<Transaction>> getAllTransactions() {

        List<Transaction> transactions = this.transactionRepository.findAll();

        return new ResponseEntity<>(transactions, HttpStatus.OK);

    }

    public boolean validateTransaction(User sender, BigDecimal value) throws Exception {
        if(sender.getUserType() == UserType.MERCHANT)
            throw new Exception("Usuarios do tipo logista nao podem enviar transacoes");

        if(sender.getBalance().compareTo(value) > 0)
            throw new Exception("Saldo insuficiente");

        return true;
    }
    public void authTransaction(User sender, BigDecimal value) throws Exception {
        ResponseEntity<Map> authResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);

        boolean authorizeTransaction = (authResponse.getStatusCode() == HttpStatus.OK && "success".equals(authResponse.getBody().get("status")));

        if (!authorizeTransaction)
            throw new Exception("Transacao nao autorizada pelo sistema!");

    }
}

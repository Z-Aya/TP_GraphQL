package ma.projet.graph.controllers;

import lombok.AllArgsConstructor;
import ma.projet.graph.entities.Compte;
import ma.projet.graph.entities.Transaction;
import ma.projet.graph.entities.TransactionRequest;
import ma.projet.graph.entities.TypeTransaction;
import ma.projet.graph.repositories.CompteRepository;
import ma.projet.graph.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;

import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class TransactionControllerGraphQL {

    @Autowired
    private final CompteRepository compteRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @QueryMapping
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @QueryMapping
    public List<Transaction> getTransactionsByCompte(@Argument Long compteId) {

        Compte compte = compteRepository.findById(compteId)
                .orElseThrow(() -> new RuntimeException("Compte not found with id: " + compteId));
        return transactionRepository.findByCompteId(compteId);
    }

    @QueryMapping
    public Map<String, Object> transactionStats() {
        long count = transactionRepository.count();
        double sumDepots = transactionRepository.sumByType(TypeTransaction.DEPOT);
        double sumRetraits = transactionRepository.sumByType(TypeTransaction.RETRAIT);
        return Map.of(
                "count", count,
                "sumDepots", sumDepots,
                "sumRetraits", sumRetraits
        );

    }

    @MutationMapping
    public Transaction addTransaction(@Argument("transaction") TransactionRequest transactionRequest) {
        if (transactionRequest == null) {
            throw new RuntimeException("Le paramètre transactionRequest est null !");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(transactionRequest.getDate());
        } catch (Exception e) {
            throw new RuntimeException("Format de date invalide : " + transactionRequest.getDate());
        }

        Compte compte = compteRepository.findById(transactionRequest.getCompteId())
                .orElseThrow(() -> new RuntimeException("Compte introuvable avec l'ID : " + transactionRequest.getCompteId()));

        Transaction transaction = new Transaction();
        transaction.setCompte(compte);
        transaction.setMontant(transactionRequest.getMontant());
        transaction.setDateTransaction(parsedDate);
        transaction.setType(transactionRequest.getType());
        return transactionRepository.save(transaction);
    }



}
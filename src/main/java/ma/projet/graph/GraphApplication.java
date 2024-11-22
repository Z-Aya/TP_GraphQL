package ma.projet.graph;

import ma.projet.graph.controllers.CompteControllerGraphQL;
import ma.projet.graph.entities.Compte;
import ma.projet.graph.entities.Transaction;
import ma.projet.graph.entities.TypeCompte;
import ma.projet.graph.entities.TypeTransaction;
import ma.projet.graph.repositories.CompteRepository;
import ma.projet.graph.repositories.TransactionRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class GraphApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphApplication.class, args);
	}


	@Bean
	CommandLineRunner start(CompteRepository compteRepository, TransactionRepository transactionRepository){
		return args -> {
			// Initialisation des comptes
			Compte c1 = compteRepository.save(new Compte(null, Math.random()*9000, new Date(), TypeCompte.EPARGNE));
			Compte c2 = compteRepository.save(new Compte(null, Math.random()*9000, new Date(), TypeCompte.COURANT));
			Compte c3 = compteRepository.save(new Compte(null, Math.random()*9000, new Date(), TypeCompte.EPARGNE));
			transactionRepository.save(new Transaction(null, 2000, new Date(), TypeTransaction.DEPOT, c1));
			transactionRepository.save(new Transaction(null, 2000, new Date(), TypeTransaction.DEPOT, c3));
			transactionRepository.save(new Transaction(null, 2000, new Date(), TypeTransaction.DEPOT, c2));
		};
	}
}
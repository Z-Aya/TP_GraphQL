package ma.projet.graph.repositories;

import ma.projet.graph.entities.Transaction;
import ma.projet.graph.entities.TypeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCompteId(Long compteId);

    @Query("SELECT COALESCE(SUM(t.montant), 0.0) FROM Transaction t WHERE t.type = :typeTransaction")
    double sumByType(@Param("typeTransaction") TypeTransaction typeTransaction);
}
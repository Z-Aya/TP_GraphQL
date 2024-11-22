package ma.projet.graph.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private Long compteId;
    private float montant;
    private String date;
    private TypeTransaction type;
}
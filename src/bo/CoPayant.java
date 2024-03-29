package bo;

import java.sql.SQLOutput;

public class CoPayant extends Compte {
    public CoPayant() {
        super();
    }

    public CoPayant(int idCompte, double solde, int idAgence) {
        super(idCompte, solde, idAgence);
    }

    // Permet de créditer un compte payant
    @Override
    public boolean versement(double montant) {
        if(montant > 0){
            double commission = (montant * 5 /100);
            solde += (montant - commission);
            System.out.println("Versement effectué");
            return true;
        } else {
            System.out.println("Versement impossible. Entrer un montant positif");
            return false;
        }
    }

    // Permet de débiter un compte payant
    @Override
    public boolean retrait(double montant) {
        if(montant > 0 ){
            double commission = (montant * 5 /100);
            if((solde - (montant + commission)) > 0){
                solde -= (montant + commission);
                System.out.println("Retrait effectué");
                return true;
            } else {
                System.out.println("Solde insuffisant pour ce retrait");
                return false;
            }
        } else {
            System.out.println("Versement impossible. Entrer un montant positif");
            return false;
        }
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CoPayant{");
        sb.append("idCompte=").append(idCompte);
        sb.append(", solde=").append(solde);
        sb.append(", idAgence=").append(idAgence);
        sb.append('}');
        return sb.toString();
    }
}

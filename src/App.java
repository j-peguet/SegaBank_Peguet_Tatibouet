import bo.*;
import dal.*;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner( System.in );
        int codeResponse = 0;
        boolean first = true;
        do {
            if (!first) {
                System.out.println("Menu introuvable, veuillez retaper un code valide");
            }
            Menu.getMenu();
            try {
                codeResponse = sc.nextInt();
            } catch (InputMismatchException e) {
                codeResponse = -1;
            } finally {
                sc.nextLine();
            }
            first = false;

        } while (codeResponse < 1 || codeResponse > 15);
        switch ( codeResponse ) {
            case 1:
                System.out.println("Création d'une agence");
                CreateAgency();
                break;
            case 2:
                System.out.println("Modifer une agence");
                EditAgency();
                break;
            case 3:
                System.out.println("Supprimer une agence");
                DeleteAgency();
                break;
            case 4:
                System.out.println("Lister les comptes");
                getListAccounts();
                break;
            case 5:
                System.out.println("Trouver un compte");
                getAccount();
                break;
            case 6:
                System.out.println("Création d'un compte simple");
                CreateSimpleAccount();
                break;
            case 7:
                System.out.println("Opréations sur un compte simple");
                TransactionSimpleAccount();
                break;
            case 8:
                System.out.println("Supprimer un compte simple");
                DeleteSimpleAccount();
                break;
            case 9:
                System.out.println("Opréations sur un compte épargne");
                CreateSavingAccount();
                break;
            case 10:
                System.out.println("Choix 10");
                TransactionSavingAccount();
                break;
            case 11:
                System.out.println("Supprimer un compte épargne");
                DeleteSavingAccount();
                break;
            case 12:
                System.out.println("Création d'un compte payant");
                CreatePayingAccount();
                break;
            case 13:
                System.out.println("Opréations sur un compte payant");
                TransactionPayingAccount();
                break;
            case 14:
                System.out.println("Supprimer un compte payant");
                DeletePayingAccount();
                break;
            case 15:
                System.out.println("Quitter le menu");
                break;
        }
    }

    // Fonctions tests
    public static void testBDD() throws IOException {
        Agence ag = new Agence(2,  "A380", "15 rue du crack boursier");
        Agence ag2 = new Agence(2,  "EP56", "18 route de la fortune");
        AgenceDAO adao = new AgenceDAO();
        //ag = adao.create(ag);
        //ag = adao.update(ag, ag2);
        //System.out.println(ag.toString());
        //System.out.println(adao.findAll(ag).toString());
        //System.out.println(adao.findById(ag.getIdAgence()).toString());
        //adao.delete(cp.getIdCompte());

        CoPayant cp = new CoPayant(2, 8000, 1);
        CoPayantDAO cpdao = new CoPayantDAO();
        cp = cpdao.create(cp);
        CoPayant CoPTempo = new CoPayant(cp.getIdCompte(), cp.getSolde(), cp.getIdAgence());
        CoPTempo.versement(1500);
        cp = cpdao.update(cp, CoPTempo);
        OperationDAO odao = new OperationDAO();
        odao.listerOperations(cp.getIdCompte());
        //System.out.println(cp.toString());
        //System.out.println(cpdao.findAll(cp).toString());
        System.out.println(cpdao.findById(28).toString());
        //cpdao.delete(cp.getIdCompte());
        Agence agence = new Agence(1, "Nantes", "2 rue des madelaines");
        agence.ListerComptes(cpdao.findAll());
    }
    public static void testClasses(){
        CoEpargne ce = new CoEpargne(13, 1000, 10, 10);
        System.out.println(ce.toString());
        ce.calculTauxInteret();
        System.out.println(ce.toString());
        ce.versement(100.30);
        ce.versement(-330);
        ce.retrait(10);
        ce.retrait(10000);
        System.out.println(ce.toString());

        CoPayant cp = new CoPayant(2, 2000, 2);
        cp.versement(100);
        cp.retrait(10);
        System.out.println(cp.toString());

        CoEpargne cep = new CoEpargne(14, 3000, 1000, 30);
        cep.retrait(1200);
        System.out.println(ce.toString());
    }

    // Fonctions Menu
    public static String userEntryCodeAgence(String codeAgency){
        Scanner sc = new Scanner( System.in );
        System.out.println("Saisir le nom de l'agence : ");
        codeAgency = sc.nextLine();
        return codeAgency;
    }
    public static String userEntryAdAgence(String adAgence){
        Scanner sc = new Scanner( System.in );
        System.out.println("Saisir le nom de l'agence : ");
        adAgence = sc.nextLine();
        return adAgence;
    }

    public static void CreateAgency(){
        Scanner sc = new Scanner(System.in);

        // Saisie utilisateur
        String codeAgency = "";
        String adAgence = "";
        codeAgency = userEntryCodeAgence(codeAgency);
        adAgence = userEntryAdAgence(adAgence);

        // Création de l'agence
        Agence newAgency = new Agence(1, codeAgency, adAgence);
        AgenceDAO adao = new AgenceDAO();
        newAgency = adao.create(newAgency);
        System.out.println(newAgency.toString());

        // Modifier l'agence
        System.out.println("Voulez-vous la modifier ?");
        System.out.println("1 - Oui");
        System.out.println("2- Non");
        System.out.print("Entrez votre choix : ");
        int choice = sc.nextInt();

        switch (choice){
            case 1:
                // Saisie utlisateur
                String codeAgency2 = "";
                String adAgence2 = "";
                codeAgency2 = userEntryCodeAgence(codeAgency2);
                adAgence2 = userEntryAdAgence(adAgence2);

                // Modification
                Agence currentAgency = new Agence(1, codeAgency2, adAgence2);
                currentAgency = adao.update(newAgency, currentAgency);
                System.out.println(currentAgency.toString());
                break;
            case 2:
                break;
        }
    }
    public static void EditAgency(){
        AgenceDAO adao = new AgenceDAO();
        Scanner sc = new Scanner(System.in);

        // Liste des agences stockées en BDD
        System.out.println("Liste des agences");
        String listAgencies = adao.findAll().toString();
        System.out.println(listAgencies);

        // Saisie utilisateur
        System.out.println("Saisir l'id de l'agence à modifier : ");
        int IdChoice = sc.nextInt();

        // Recherche de l'agence à modifier
        Agence currentAgency = adao.findById(IdChoice);

        // Modification
        String codeAgency = "";
        String adAgence = "";
        codeAgency = userEntryCodeAgence(codeAgency);
        adAgence = userEntryAdAgence(adAgence);

        Agence newAgency = new Agence(1, codeAgency, adAgence);
        newAgency = adao.update(currentAgency,  newAgency);
        System.out.println(newAgency.toString());
    }
    public static void DeleteAgency(){
        AgenceDAO adao = new AgenceDAO();
        Scanner sc = new Scanner(System.in);

        // Liste des agences stockées en BDD
        System.out.println("Liste des agences");
        String listAgencies = adao.findAll().toString();
        System.out.println(listAgencies);

        // Saisie utilisateur
        System.out.println("Saisir l'id de l'agence à supprimer : ");
        int IdChoice = sc.nextInt();

        // Recherche de l'agence à supprimer
        Agence currentAgency = adao.findById(IdChoice);

        System.out.println("Etes-vous sûr de vouloir supprimer l'agence "+ currentAgency.toString() +" ?");
        System.out.println("1 - Oui");
        System.out.println("2- Non");
        System.out.print("Entrez votre choix : ");
        int choice = sc.nextInt();

        switch (choice){
            case 1:
                adao.delete(IdChoice);
                break;
            case 2:
                break;
        }
    }

    // Liste des comptes
    public static void getListAccounts(){
        CoSimpleDAO csdao = new CoSimpleDAO();
        CoEpargneDAO cedao = new CoEpargneDAO();
        CoPayantDAO cpdao = new CoPayantDAO();

        System.out.println("Liste des comptes");
        System.out.println("_____Comptes Simples_____");
        String listCs = csdao.findAll().toString();
        System.out.println(listCs);
        System.out.println("_____Comptes Epargnes_____");
        String listCe = cedao.findAll().toString();
        System.out.println(listCe);
        System.out.println("_____Comptes Payants_____");
        String listCp = cpdao.findAll().toString();
        System.out.println(listCp);
    }
    public static void getAccount(){
        CoSimpleDAO csdao = new CoSimpleDAO();
        CoEpargneDAO cedao = new CoEpargneDAO();
        CoPayantDAO cpdao = new CoPayantDAO();

        System.out.println("Liste des comtpes existants");
        getListAccounts();

        Scanner sc = new Scanner(System.in);
        System.out.println("Quel type de compte voulez-vous consulter ?");
        System.out.println("1 - Compte simple");
        System.out.println("2 - Compte épargne");
        System.out.println("3 - Compte payant");
        System.out.println("Entrez votre choix");
        int accountChoice = sc.nextInt();

        System.out.println("Entrez l'id du compte à consulter");
        int idAccount = sc.nextInt();

        if (accountChoice == 1){
            CoSimple currentAccount = csdao.findById(idAccount);
            System.out.println(currentAccount.toString());
        } else if(accountChoice == 2){
            CoEpargne currentAccount = cedao.findById(idAccount);
            System.out.println(currentAccount.toString());
        } else {
            CoPayant currentAccount = cpdao.findById(idAccount);
            System.out.println(currentAccount.toString());
        }
    }

    // Création
    public static void CreateSimpleAccount() {
        AgenceDAO adao = new AgenceDAO();
        CoSimpleDAO csdao = new CoSimpleDAO();
        Scanner sc = new Scanner(System.in);

        // Saisie Utilisateur Compte
        System.out.println("Entrez le solde du compte");
        Double balance = sc.nextDouble();
        System.out.println("Entrez le découvert du compte");
        Double overdraft = sc.nextDouble();

        // Liste des agences stockées en BDD
        System.out.println("Liste des agences existantes");
        String listAgencies = adao.findAll().toString();
        System.out.println(listAgencies);

        System.out.println("Entrez l'id de l'agence");
        int idAgence = sc.nextInt();

        // Création du compte simple
        CoSimple cs = new CoSimple(1, balance, idAgence ,overdraft);
        cs = csdao.create(cs);
        System.out.println(cs.toString());
    }
    public static void CreateSavingAccount() {
        AgenceDAO adao = new AgenceDAO();
        CoEpargneDAO cedao = new CoEpargneDAO();
        Scanner sc = new Scanner(System.in);

        // Saisie Utilisateur Compte
        System.out.println("Entrez le solde du compte");
        Double balance = sc.nextDouble();
        System.out.println("Entrez le taux d'intérêt");
        float interestRate = sc.nextFloat();

        // Liste des agences stockées en BDD
        System.out.println("Liste des agences existantes");
        String listAgencies = adao.findAll().toString();
        System.out.println(listAgencies);

        System.out.println("Entrez l'id de l'agence");
        int idAgence = sc.nextInt();

        // Création du compte épargne
        CoEpargne ce = new CoEpargne(1, balance, idAgence , interestRate);
        ce = cedao.create(ce);
        System.out.println(ce.toString());
    }
    public static void CreatePayingAccount() {
        AgenceDAO adao = new AgenceDAO();
        CoPayantDAO cpdao = new CoPayantDAO();
        Scanner sc = new Scanner(System.in);

        // Saisie Utilisateur Compte
        System.out.println("Entrez le solde du compte");
        Double balance = sc.nextDouble();

        // Liste des agences stockées en BDD
        System.out.println("Liste des agences existantes");
        String listAgencies = adao.findAll().toString();
        System.out.println(listAgencies);

        System.out.println("Entrez l'id de l'agence");
        int idAgence = sc.nextInt();

        // Création du compte épargne
        CoPayant cp = new CoPayant(1, balance, idAgence);
        cp = cpdao.create(cp);
        System.out.println(cp.toString());
    }

    // Modfication
    public static void TransactionSimpleAccount(){
        AgenceDAO adao = new AgenceDAO();
        Scanner sc = new Scanner(System.in);
        CoSimpleDAO csdao = new CoSimpleDAO();

        System.out.println("Liste des comptes simples");
        String listCs = csdao.findAll().toString();
        System.out.println(listCs);

        // Saisie utilisateur
        System.out.println("Saisir l'id du compte : ");
        int IdChoice = sc.nextInt();

        // Recherche de du compte à modifier
        CoSimple currentAccount = csdao.findById(IdChoice);
        System.out.println(currentAccount.toString());

        // Récupération des donnés du compte choisi
        CoSimple tmpAccount = new CoSimple(currentAccount.getIdCompte(), currentAccount.getSolde(), currentAccount.getIdAgence(), currentAccount.getDecouvert());

        // Opréations versement et retrait
        System.out.println("Quelle opération voulez-vous effectuer ?");
        System.out.println("1 - Versement");
        System.out.println("2 - Retrait");
        System.out.println("3 - Modifier le découvert");
        System.out.println("4 - Changer d'agence");
        System.out.println("Entrez votre choix");
        int transactionChoice = sc.nextInt();

        Scanner sc2 = new Scanner(System.in);

        switch (transactionChoice){
            case 1:
                System.out.println("Entrez le montant à verser");
                double amountDeposit = sc2.nextDouble();
                tmpAccount.versement(amountDeposit);
                currentAccount = csdao.update(currentAccount, tmpAccount);
                System.out.println(currentAccount.toString());
                break;
            case 2:
                System.out.println("Entrez le montant à retirer");
                double amountWithdrawal = sc2.nextDouble();
                tmpAccount.retrait(amountWithdrawal);
                currentAccount = csdao.update(currentAccount, tmpAccount);
                System.out.println(currentAccount.toString());
                break;
            case 3 :
                System.out.println("Entrez le montant du découvert");
                Double overdraft = sc2.nextDouble();
                CoSimple tmpAccount2 = new CoSimple(currentAccount.getIdCompte(), currentAccount.getSolde(), currentAccount.getIdAgence(), overdraft);
                currentAccount = csdao.update(currentAccount, tmpAccount2);
                System.out.println(currentAccount.toString());
                break;
            case 4:
                System.out.println("Liste des agences existantes");
                String listAgencies = adao.findAll().toString();
                System.out.println(listAgencies);

                System.out.println("Entrez l'id de l'agence");
                int idAgence = sc2.nextInt();
                CoSimple tmpAccount3 = new CoSimple(currentAccount.getIdCompte(), currentAccount.getSolde(), idAgence, currentAccount.getDecouvert());
                currentAccount = csdao.update(currentAccount, tmpAccount3);
                System.out.println(currentAccount.toString());
                break;
        }
    }
    public static void TransactionSavingAccount(){
        AgenceDAO adao = new AgenceDAO();
        Scanner sc = new Scanner(System.in);
        CoEpargneDAO cedao = new CoEpargneDAO();

        System.out.println("Liste des comptes épargnes");
        String listCe = cedao.findAll().toString();
        System.out.println(listCe);

        // Saisie utilisateur
        System.out.println("Saisir l'id du compte : ");
        int IdChoice = sc.nextInt();

        // Recherche de du compte à modifier
        CoEpargne currentAccount = cedao.findById(IdChoice);
        System.out.println(currentAccount.toString());

        // Récupération des donnés du compte choisi
        CoEpargne tmpAccount = new CoEpargne(currentAccount.getIdCompte(), currentAccount.getSolde(), currentAccount.getIdAgence(), currentAccount.getTauxInteret());

        // Opréations versement et retrait
        System.out.println("Quelle opération voulez-vous effectuer ?");
        System.out.println("1 - Versement");
        System.out.println("2 - Retrait");
        System.out.println("3 - Modifier le taux d'intérêt");
        System.out.println("4 - Changer d'agence");
        System.out.println("Entrez votre choix");
        int transactionChoice = sc.nextInt();

        Scanner sc2 = new Scanner(System.in);

        switch (transactionChoice){
            case 1:
                System.out.println("Entrez le montant à verser");
                double amountDeposit = sc2.nextDouble();
                tmpAccount.versement(amountDeposit);
                currentAccount = cedao.update(currentAccount, tmpAccount);
                System.out.println(currentAccount.toString());
                break;
            case 2:
                System.out.println("Entrez le montant à retirer");
                double amountWithdrawal = sc2.nextDouble();
                tmpAccount.retrait(amountWithdrawal);
                currentAccount = cedao.update(currentAccount, tmpAccount);
                System.out.println(currentAccount.toString());
                break;
            case 3 :
                System.out.println("Entrez le taux d'intérêt");
                float interestRate = sc2.nextFloat();
                CoEpargne tmpAccount2 = new CoEpargne(currentAccount.getIdCompte(), currentAccount.getSolde(), currentAccount.getIdAgence(), interestRate);
                currentAccount = cedao.update(currentAccount, tmpAccount2);
                System.out.println(currentAccount.toString());
                break;
            case 4:
                System.out.println("Liste des agences existantes");
                String listAgencies = adao.findAll().toString();
                System.out.println(listAgencies);

                System.out.println("Entrez l'id de l'agence");
                int idAgence = sc2.nextInt();
                CoEpargne tmpAccount3 = new CoEpargne(currentAccount.getIdCompte(), currentAccount.getSolde(), idAgence, currentAccount.getTauxInteret());
                currentAccount = cedao.update(currentAccount, tmpAccount3);
                System.out.println(currentAccount.toString());
                break;
        }
    }
    public static void TransactionPayingAccount(){
        AgenceDAO adao = new AgenceDAO();
        Scanner sc = new Scanner(System.in);
        CoPayantDAO cpdao = new CoPayantDAO();

        System.out.println("Liste des comptes épargnes");
        String listCp = cpdao.findAll().toString();
        System.out.println(listCp);

        // Saisie utilisateur
        System.out.println("Saisir l'id du compte : ");
        int IdChoice = sc.nextInt();

        // Recherche de du compte à modifier
        CoPayant currentAccount = cpdao.findById(IdChoice);
        System.out.println(currentAccount.toString());

        // Récupération des donnés du compte choisi
        CoPayant tmpAccount = new CoPayant(currentAccount.getIdCompte(), currentAccount.getSolde(), currentAccount.getIdAgence());

        // Opréations versement et retrait
        System.out.println("Quelle opération voulez-vous effectuer ?");
        System.out.println("1 - Versement");
        System.out.println("2 - Retrait");
        System.out.println("3 - Changer d'agence");
        System.out.println("Entrez votre choix");
        int transactionChoice = sc.nextInt();

        Scanner sc2 = new Scanner(System.in);

        switch (transactionChoice){
            case 1:
                System.out.println("Entrez le montant à verser");
                double amountDeposit = sc2.nextDouble();
                tmpAccount.versement(amountDeposit);
                currentAccount = cpdao.update(currentAccount, tmpAccount);
                System.out.println(currentAccount.toString());
                break;
            case 2:
                System.out.println("Entrez le montant à retirer");
                double amountWithdrawal = sc2.nextDouble();
                tmpAccount.retrait(amountWithdrawal);
                currentAccount = cpdao.update(currentAccount, tmpAccount);
                System.out.println(currentAccount.toString());
                break;
            case 3:
                System.out.println("Liste des agences existantes");
                String listAgencies = adao.findAll().toString();
                System.out.println(listAgencies);

                System.out.println("Entrez l'id de l'agence");
                int idAgence = sc2.nextInt();
                CoPayant tmpAccount3 = new CoPayant(currentAccount.getIdCompte(), currentAccount.getSolde(), idAgence);
                currentAccount = cpdao.update(currentAccount, tmpAccount3);
                System.out.println(currentAccount.toString());
                break;
        }
    }

    // Suppression
    public static void DeleteSimpleAccount(){
        CoSimpleDAO csdao = new CoSimpleDAO();
        Scanner sc = new Scanner(System.in);

        // Liste des agences stockées en BDD
        System.out.println("Liste des comptes simples");
        String listCs = csdao.findAll().toString();
        System.out.println(listCs);

        // Saisie utilisateur
        System.out.println("Saisir l'id de l'agence à supprimer : ");
        int IdChoice = sc.nextInt();

        // Recherche de l'agence à supprimer
        CoSimple currentAccount = csdao.findById(IdChoice);

        System.out.println("Etes-vous sûr de vouloir supprimer l'agence "+ currentAccount.toString() +" ?");
        System.out.println("1 - Oui");
        System.out.println("2- Non");
        System.out.print("Entrez votre choix : ");
        int choice = sc.nextInt();

        switch (choice){
            case 1:
                csdao.delete(IdChoice);
                break;
            case 2:
                break;
        }
    }
    public static void DeleteSavingAccount(){
        CoEpargneDAO cedao = new CoEpargneDAO();
        Scanner sc = new Scanner(System.in);

        // Liste des agences stockées en BDD
        System.out.println("Liste des comptes épargne");
        String listCe = cedao.findAll().toString();
        System.out.println(listCe);

        // Saisie utilisateur
        System.out.println("Saisir l'id de l'agence à supprimer : ");
        int IdChoice = sc.nextInt();

        // Recherche de l'agence à supprimer
        CoEpargne currentAccount = cedao.findById(IdChoice);

        System.out.println("Etes-vous sûr de vouloir supprimer l'agence "+ currentAccount.toString() +" ?");
        System.out.println("1 - Oui");
        System.out.println("2- Non");
        System.out.print("Entrez votre choix : ");
        int choice = sc.nextInt();

        switch (choice){
            case 1:
                cedao.delete(IdChoice);
                break;
            case 2:
                break;
        }
    }
    public static void DeletePayingAccount(){
        CoPayantDAO cpdao = new CoPayantDAO();
        Scanner sc = new Scanner(System.in);

        // Liste des agences stockées en BDD
        System.out.println("Liste des comptes épargne");
        String listCe = cpdao.findAll().toString();
        System.out.println(listCe);

        // Saisie utilisateur
        System.out.println("Saisir l'id de l'agence à supprimer : ");
        int IdChoice = sc.nextInt();

        // Recherche de l'agence à supprimer
        CoPayant currentAccount = cpdao.findById(IdChoice);

        System.out.println("Etes-vous sûr de vouloir supprimer l'agence "+ currentAccount.toString() +" ?");
        System.out.println("1 - Oui");
        System.out.println("2- Non");
        System.out.print("Entrez votre choix : ");
        int choice = sc.nextInt();

        switch (choice){
            case 1:
                cpdao.delete(IdChoice);
                break;
            case 2:
                break;
        }
    }
}

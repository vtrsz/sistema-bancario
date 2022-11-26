package org.mesttra.dao;

import org.mesttra.factory.ConnectionFactory;
import org.mesttra.service.Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClientDAO {
    private static Connection connection;

    public ClientDAO() { connection = ConnectionFactory.getConnection(); }

    public static int getHigherValue(int x, int y) {
        if (x == 0 && y == 0) {
            System.out.println("ESTA FUNCIONANDO");
        }
        try {
            if (x > y) {
                return x;
            } else if (y > x) {
                return y;
            }
        } catch (Exception ignored) {}
        return 0;
    }

    public static int getNextAccountNumber() {
        String query = "SELECT account_number FROM natural_person ORDER BY account_number DESC LIMIT 1;";
        String query2 = "SELECT account_number FROM legal_person ORDER BY account_number DESC LIMIT 1;";
        int lastAccountNumberFromNaturalPerson = 0;
        int lastAccountNumberFromLegalPerson = 0;
        int accountNumber = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet firstResult = stmt.executeQuery();
            if (firstResult.next()) {
                lastAccountNumberFromNaturalPerson = firstResult.getInt("account_number");
            }
            stmt.close();

            stmt = connection.prepareStatement(query2);
            ResultSet secondResult = stmt.executeQuery();
            if (secondResult.next()) {
                lastAccountNumberFromLegalPerson = secondResult.getInt("account_number");
            }
            stmt.close();

            accountNumber = getHigherValue(lastAccountNumberFromLegalPerson, lastAccountNumberFromNaturalPerson) + 1;
        } catch (Exception ex) {

        }
        return accountNumber;
    }

    public static boolean remove(int accountNumber) {
        String strQuery = "DELETE FROM $tableName WHERE account_number = ?";

        try {
            String query = strQuery.replace("$tableName", Operations.getClientTypeByAccountNumber(accountNumber));
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, accountNumber);
            stmt.execute();
            stmt.close();
        } catch (Exception ex) {
            System.err.println("[ERROR] Não foi possivel realizar a remoção.");
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public static boolean updateOverDraft(int accountNumber, double value) {
        String strQuery = "UPDATE $tableName SET over_draft = ? WHERE account_number = ?";

        try {
            String query = strQuery.replace("$tableName", Operations.getClientTypeByAccountNumber(accountNumber));
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDouble(1, value);
            stmt.setInt(2, accountNumber);
            stmt.execute();
            stmt.close();
        } catch (Exception ex) {
            System.err.println("Não foi possível atualizar o limite do cheque especial!");
            return false;
        }
        return true;
    }

    public static boolean transferAmount(int fromAccountNumber, int toAccountNumber, double value) {
        String strQuery = "UPDATE $tableName SET amount = amount - ? WHERE account_number = ?";
        String strQuery2 = "UPDATE $tableName SET amount = amount + ? WHERE account_number = ?";

        try {
            String query = strQuery.replace("$tableName", Operations.getClientTypeByAccountNumber(fromAccountNumber));
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDouble(1, value);
            stmt.setInt(2, fromAccountNumber);
            stmt.execute();
            stmt.close();

            String query2 = strQuery2.replace("$tableName", Operations.getClientTypeByAccountNumber(toAccountNumber));
            stmt = connection.prepareStatement(query2);
            stmt.setDouble(1, value);
            stmt.setInt(2, toAccountNumber);
            stmt.execute();
            stmt.close();
        } catch (Exception ex) {
            System.err.println("Não foi possível realizar a transferência!");
            return false;
        }
        return true;
    }

    public static boolean addAmount(int accountNumber, double value) {
        String strQuery = "UPDATE $tableName SET amount = amount + ? WHERE account_number = ?";

        try {
            String query = strQuery.replace("$tableName", Operations.getClientTypeByAccountNumber(accountNumber));
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDouble(1, value);
            stmt.setInt(2, accountNumber);
            stmt.execute();
            stmt.close();
        } catch (Exception ex) {
            System.err.println("Não foi possível adicionar saldo nesta conta!");
            return false;
        }
        return true;
    }
}

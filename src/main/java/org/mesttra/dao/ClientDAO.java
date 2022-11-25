package org.mesttra.dao;

import org.mesttra.factory.ConnectionFactory;
import org.mesttra.service.Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ClientDAO {
    private static Connection connection;

    public ClientDAO() { connection = ConnectionFactory.getConnection(); }

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

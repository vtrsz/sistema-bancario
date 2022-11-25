package org.mesttra.dao;

import org.mesttra.factory.ConnectionFactory;
import org.mesttra.pojo.NaturalPersonPOJO;
import org.mesttra.service.Operations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NaturalPersonDAO {
    private static Connection connection;

    public NaturalPersonDAO() {
        connection = ConnectionFactory.getConnection();
    }

    public static boolean insert(NaturalPersonPOJO client) {
        String query = "INSERT INTO " +
                        "natural_person (account_number, agency, phone_number, amount, over_draft, cpf, name, age)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, client.getAccountNumber());
            stmt.setInt(2, client.getAgency());
            stmt.setString(3, client.getPhoneNumber());
            stmt.setDouble(4, client.getAmount());
            stmt.setDouble(5, client.getOverDraft());
            stmt.setString(6, client.getCpf());
            stmt.setString(7, client.getName());
            stmt.setInt(8, client.getAge());
            stmt.execute();
            stmt.close();
        } catch (Exception ex) {
            System.err.println("[ERROR] Não foi possivel realizar inserção.");
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public static boolean remove(int accountNumber) {
        String query = "DELETE FROM natural_person WHERE account_number = ?";

        try {
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

    public static List<NaturalPersonPOJO> getAllClients() {
        String query = "SELECT * FROM natural_person";

        List<NaturalPersonPOJO> clients = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
            stmt.close();
            fillList(clients, result);
        } catch (Exception ex) {
            System.err.println("[ERROR] Não foi possivel obter a lista de pessoas físicas.");
            System.err.println(ex.getMessage());
        }
        return clients;
    }

    public static NaturalPersonPOJO findClientByAccountNumber(int accountNumber) {
        String query = "SELECT * FROM natural_person WHERE account_number = ?";

        NaturalPersonPOJO client = null;

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, accountNumber);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                client = fillClient(result);
            }
            stmt.close();
        } catch (Exception ex) {
            System.err.println("Não foi possível identificar nenhum cliente com este numero de conta!");
        }
        return client;
    }

    public static boolean updateOverDraft(int accountNumber, double value) {
        String query = "UPDATE natural_person SET over_draft = ? WHERE account_number = ?";
        try {
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

            String query2 = strQuery.replace("$tableName", Operations.getClientTypeByAccountNumber(toAccountNumber));
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
        String query = "UPDATE natural_person SET amount = amount + ? WHERE account_number = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setDouble(1, value);
            stmt.setInt(2, accountNumber);
            stmt.execute();
            stmt.close();
        } catch (Exception ex) {
            System.out.println("Não foi possível adicionar saldo nesta conta!");
            return false;
        }
        return true;
    }

    private static NaturalPersonPOJO fillClient(ResultSet result) throws SQLException {
        NaturalPersonPOJO client = new NaturalPersonPOJO();

        client.setAccountNumber(result.getInt("account_number"));
        client.setAgency(result.getInt("agency"));
        client.setPhoneNumber(result.getString("phone_number"));
        client.setAmount(result.getDouble("amount"));
        client.setOverDraft(result.getDouble("over_draft"));
        client.setCpf(result.getString("cpf"));
        client.setName(result.getString("name"));
        client.setAge(result.getInt("age"));
        return client;
    }

    private static void fillList(List<NaturalPersonPOJO> clients, ResultSet result) throws SQLException {
        while (result.next()) {
            NaturalPersonPOJO client = fillClient(result);
            clients.add(client);
        }
    }
}

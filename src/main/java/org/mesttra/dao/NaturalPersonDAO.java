package org.mesttra.dao;

import org.mesttra.factory.ConnectionFactory;
import org.mesttra.pojo.NaturalPersonPOJO;

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

    public boolean insert(NaturalPersonPOJO client) {
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

    public static List<NaturalPersonPOJO> getAllClients() {

        String query = "SELECT * FROM natural_person";

        List<NaturalPersonPOJO> clients = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                fillList(clients, result);
            }
            stmt.close();
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

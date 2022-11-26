package org.mesttra.dao;

import org.mesttra.factory.ConnectionFactory;
import org.mesttra.pojo.LegalPersonPOJO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LegalPersonDAO {
    private static Connection connection;

    public LegalPersonDAO() {
        connection = ConnectionFactory.getConnection();
    }

    public boolean insert(LegalPersonPOJO client) {
        String query = "INSERT INTO " +
                "legal_person (account_number, agency, phone_number, amount, over_draft, cnpj, social_reason, fantasy_name)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, client.getAccountNumber());
            stmt.setInt(2, client.getAgency());
            stmt.setString(3, client.getPhoneNumber());
            stmt.setDouble(4, client.getAmount());
            stmt.setDouble(5, client.getOverDraft());
            stmt.setString(6, client.getCpnj());
            stmt.setString(7, client.getSocialReason());
            stmt.setString(8, client.getFantasyName());
            stmt.execute();
            stmt.close();
        } catch (Exception ex) {
            System.err.println("[ERROR] Não foi possivel realizar inserção.");
            System.err.println(ex.getMessage());
            return false;
        }
        return true;
    }

    public static List<LegalPersonPOJO> getAllClients() {
        String query = "SELECT * FROM legal_person";

        List<LegalPersonPOJO> clients = new ArrayList<>();

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet result = stmt.executeQuery();
            fillList(clients, result);
            stmt.close();
        } catch (Exception ex) {
            System.err.println("[ERROR] Não foi possivel obter a lista de pessoas jurídicas.");
            System.err.println(ex.getMessage());
        }
        return clients;
    }

    public static LegalPersonPOJO findClientByAccountNumber(int accountNumber) {
        String query = "SELECT * FROM legal_person WHERE account_number = ?";

        LegalPersonPOJO client = null;

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

    private static LegalPersonPOJO fillClient(ResultSet result) throws SQLException {
        LegalPersonPOJO client = new LegalPersonPOJO();

        client.setAccountNumber(result.getInt("account_number"));
        client.setAgency(result.getInt("agency"));
        client.setPhoneNumber(result.getString("phone_number"));
        client.setAmount(result.getDouble("amount"));
        client.setOverDraft(result.getDouble("over_draft"));
        client.setCpnj(result.getString("cnpj"));
        client.setSocialReason(result.getString("social_reason"));
        client.setFantasyName(result.getString("fantasy_name"));
        return client;
    }

    private static List<LegalPersonPOJO> fillList(List<LegalPersonPOJO> clients, ResultSet result) throws SQLException {
        while (result.next()) {
            LegalPersonPOJO client = fillClient(result);
            clients.add(client);
        }
        return clients;
    }
}


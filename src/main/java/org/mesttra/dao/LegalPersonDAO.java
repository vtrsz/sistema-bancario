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


    public class legalPersonDAO {
        private static Connection connection;

        public legalPersonDAO() {
            connection = ConnectionFactory.getConnection();
        }

        public static boolean insert(LegalPersonPOJO client) {
            String query = "INSERT INTO " +
                    "legal_person (account_number, agency, phone_number, amount, over_draft, cpnj, socialReason, fantasyName)" +
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

        public static boolean remove(int accountNumber) {
            String query = "DELETE FROM legal_person WHERE account_number = ?";

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

        public static List<LegalPersonPOJO> getAllClients() {
            String query = "SELECT * FROM legal_person";

            List<LegalPersonPOJO> clients = new ArrayList<>();

            try {
                PreparedStatement stmt = connection.prepareStatement(query);
                ResultSet result = stmt.executeQuery();
                stmt.close();
                fillList(clients, result);
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
                stmt.close();
                client = fillClient(result);
            } catch (Exception ex) {
                System.out.println("Não foi possível identificar nenhum cliente com este numero de conta!");
            }
            return client;
        }

        public static boolean updateOverDraft(int accountNumber, double value) {
            String query = "UPDATE legal_person SET over_draft = ? WHERE account_number = ?";
            try {
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setDouble(1, value);
                stmt.setInt(2, accountNumber);
                stmt.execute();
                stmt.close();
            } catch (Exception ex) {
                System.out.println("Não foi possível atualizar o limite do cheque especial!");
                return false;
            }
            return true;
        }

        public static boolean transferAmount(int fromAccountNumber, int toAccountNumber, double value) {
            String query = "UPDATE legal_person SET amount = amount - ? WHERE account_number = ?";
            String query2 = "UPDATE legal_person SET amount = amount + ? WHERE account_number = ?";

            try {
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setDouble(1, value);
                stmt.setInt(2, fromAccountNumber);
                stmt.execute();
                stmt.close();

                stmt = connection.prepareStatement(query2);
                stmt.setDouble(1, value);
                stmt.setInt(2, toAccountNumber);
                stmt.execute();
                stmt.close();
            } catch (Exception ex) {
                System.out.println("Não foi possível realizar a transferência!");
                return false;
            }
            return true;
        }

        public static boolean addAmount(int accountNumber, double value) {
            String query = "UPDATE legal_person SET amount = amount + ? WHERE account_number = ?";

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

        private static LegalPersonPOJO fillClient(ResultSet result) throws SQLException {
            LegalPersonPOJO client = new LegalPersonPOJO();

            client.setAccountNumber(result.getInt("account_number"));
            client.setAgency(result.getInt("agency"));
            client.setPhoneNumber(result.getString("phone_number"));
            client.setAmount(result.getDouble("amount"));
            client.setOverDraft(result.getDouble("over_draft"));
            client.setSocialReason(result.getString("socialReason"));
            client.setFantasyName(result.getString("fantasyName"));
            return client;
        }

        private static void fillList(List<LegalPersonPOJO> clients, ResultSet result) throws SQLException {
            while (result.next()) {
                LegalPersonPOJO client = fillClient(result);
                clients.add(client);
            }
        }
    }
}


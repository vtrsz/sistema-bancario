package org.mesttra.service;

import org.mesttra.dao.LegalPersonDAO;
import org.mesttra.dao.NaturalPersonDAO;
import org.mesttra.pojo.LegalPersonPOJO;
import org.mesttra.pojo.NaturalPersonPOJO;

public class Operations {
    public static String getClientTypeByAccountNumber(int accountNumber) {
        try {
            NaturalPersonPOJO clientNaturalPerson = NaturalPersonDAO.findClientByAccountNumber(accountNumber);
            LegalPersonPOJO clientLegalPerson = LegalPersonDAO.findClientByAccountNumber(accountNumber);

            if (clientNaturalPerson != null) {
                return "natural_person";
            } else if (clientLegalPerson != null) {
                return "legal_person";
            } else {
                throw new Exception("Não foi possivel achar este cliente no banco de dados");
            }

        } catch (Exception ex) {
            System.err.println("Não foi possivel achar o cliente.");
            System.err.println(ex.getMessage());
            return null;
        }
    }
}

package org.mesttra.service;

import org.mesttra.dao.ClientDAO;
import org.mesttra.dao.LegalPersonDAO;
import org.mesttra.dao.NaturalPersonDAO;
import org.mesttra.pojo.ClientPOJO;
import org.mesttra.pojo.LegalPersonPOJO;
import org.mesttra.pojo.NaturalPersonPOJO;

import java.io.IOException;
import java.util.*;

public class Menu{

    private static Scanner input = new Scanner(System.in);

    private static List<ClientPOJO> listaCliente = new ArrayList<>();

    private static LegalPersonDAO legalPersonDAO = new LegalPersonDAO();

    private static NaturalPersonDAO naturalPersonDAO = new NaturalPersonDAO();

    private static ClientDAO clientDAO = new ClientDAO();


    public static int inicio() {
        System.out.println("==== Menu Principal ====");
        System.out.println();
        System.out.println("1 - Cadastrar Cliente");
        System.out.println("2 - Remover Cliente");
        System.out.println("3 - Consultar Cliente");
        System.out.println("4 - Alterar Limite");
        System.out.println("5 - Transferir");
        System.out.println("6 - Depositar");
        System.out.println("7 - Listar todos clientes");
        System.out.println("8 - Buscar cliente por numero da conta");
        System.out.println("9 - Sair");
        System.out.println();
        System.out.println("==========================");

        System.out.println();
        System.out.print("Digite a opção desejada: ");
        int entrada = entradaInteiro();

        opcaoEscolhida(entrada);

        return entrada;

    }

    private static void opcaoEscolhida(int entrada) {

        switch (entrada) {
            case 1 -> cadastrarCliente();
            case 2 -> removerCliente();
            case 3 -> listarTodosClientes();
            case 4 -> alterarLimite();
            case 5 -> transferir();
            case 6 -> depositar();
            case 7 -> listarTodosClientes();
            case 8 -> listarClientePorNumeroConta();
            case 9 -> System.out.println("Obrigado por utilizar nosso sistema!");
            default -> System.out.println("Opção inserida inválida!");
        }
        pressEnterToContinue();
        limpaConsole();

    }

    private static String entradaString(){

        String entrada;

        try{
            return entrada = input.nextLine();
        } catch(NullPointerException e){
            System.out.println("Valor inserido não pode ser nulo!");
            e.printStackTrace();
        }

        return null;
    }

    private static double entradaDouble(){

        double entrada;

        try{
            entrada = input.nextDouble();
            input.nextLine();
            return entrada;
        } catch (InputMismatchException e){
            System.out.println("O valor inserido deve ser um número!");
            input.nextLine();
        }

        return 0;
    }

    private static int entradaInteiro() {

        int entrada;

        try {
            entrada = input.nextInt();
            input.nextLine();
            return entrada;
        } catch (InputMismatchException e) {
            System.out.println("O valor inserido deve ser um número inteiro!");
            input.nextLine();
        }
        return 0;
    }
    public static void cadastrarCliente() {

        System.out.println("===== Cadastro Cliente =====");
        int tipoCliente = getTipoCliente();

        switch (tipoCliente){
            case 1:
                cadastrarClientePF();
                break;
            case 2:
                cadastrarClientePJ();
                break;
            default:
                System.out.println("Valor inserido inválido!");
        }
    }

    private static int getTipoCliente() {
        System.out.println("Selecione uma opção: ");
        System.out.println("1 - Pessoa Física");
        System.out.println("2 - Pessoa Jurídica");
        System.out.println("============================");

        System.out.println();
        System.out.print("Digite a opção desejada: ");
        return entradaInteiro();
    }

    public static void cadastrarClientePF() {
        System.out.println("===== Cadastro Cliente Pessoa Física =====");

        System.out.println("Digite abaixo os dados, conforme solicitado: ");

        System.out.print("CPF: ");
        String cpf = entradaString();

        System.out.print("Nome: ");
        String name = entradaString();

        System.out.print("Idade: ");
        int age = entradaInteiro();

        System.out.print("Telefone: ");
        String phoneNumber = entradaString();

        System.out.print("Limite de cheque especial: ");
        double overDraft = entradaDouble();

         naturalPersonDAO.insert(new NaturalPersonPOJO(phoneNumber, overDraft, cpf, name, age));

        System.out.println("Cliente cadastrado com sucesso!");
    }


    public static void cadastrarClientePJ() {
        System.out.println("===== Cadastro Cliente Pessoa Jurídica =====");

        System.out.println("Digite abaixo os dados, conforme solicitado: ");

        System.out.print("CNPJ: ");
        String cpnj = entradaString();

        System.out.print("Nome social: ");
        String socialReason = entradaString();

        System.out.print("Nome fantasia: ");
        String fantasyName = entradaString();

        System.out.print("Telefone: ");
        String phoneNumber = entradaString();

        System.out.print("Limite de cheque especial: ");
        double overDraft = entradaDouble();

        legalPersonDAO.insert(new LegalPersonPOJO(phoneNumber, overDraft, cpnj, socialReason, fantasyName));

        System.out.println("Cliente cadastrado com sucesso!");
    }

    public static void removerCliente() {
        System.out.println("==== Remover Cliente ====");
        int tipoCliente = getTipoCliente();

        String opcaoCliente;

        System.out.print("Digite o número da conta: ");
        int numeroConta = entradaInteiro();

        ClientDAO.remove(numeroConta);

    }

    public static void listarTodosClientes(){
        System.out.println("===== Listar todos os clientes ====");
        int tipoCliente = getTipoCliente();

        if(tipoCliente==1){
           naturalPersonDAO.getAllClients().forEach((cliente) -> System.out.println(cliente.ToString()));
        }else{
            legalPersonDAO.getAllClients().forEach((cliente) -> System.out.println(cliente.ToString()));
        }
    }

    public static void listarClientePorNumeroConta(){
        System.out.println("==== Listar Clientes por numero da Conta ====");
        int tipoCliente = getTipoCliente();


        System.out.print("Digite o número da conta: ");
        int numeroConta = entradaInteiro();

        if(tipoCliente==1){
            NaturalPersonPOJO naturalPersonPOJO = naturalPersonDAO.findClientByAccountNumber(numeroConta);
            System.out.println(naturalPersonPOJO.ToString());
        } else{
            LegalPersonPOJO legalPersonPOJO = legalPersonDAO.findClientByAccountNumber(numeroConta);
            System.out.println(legalPersonPOJO.ToString());
        }

    }

    public static void alterarLimite(){
        System.out.println("==== Alterar limite cheque especial ====");
        int tipoCliente = getTipoCliente();

        System.out.print("Digite o número da conta: ");
        int numeroConta = entradaInteiro();

        System.out.print("Digite o novo valor do cheque especial: ");
        double novoValorChequeEspecial = entradaDouble();

       ClientDAO.updateOverDraft(numeroConta, novoValorChequeEspecial);

    }

    public static void transferir(){
        System.out.println("==== Transferência ====");

        System.out.print("Numero da conta origem: ");
        int numeroContaOrigem = entradaInteiro();

        System.out.print("Numero da conta destino: ");
        int numeroContaDestino = entradaInteiro();

        System.out.print("Valor a ser transferido: ");
        double valorTransferencia = entradaDouble();

        ClientDAO.transferAmount(numeroContaDestino, numeroContaDestino, valorTransferencia);

    }

    public static void depositar(){
        System.out.println("==== Deposito ====");
        int tipoCliente = getTipoCliente();

        System.out.print("Digite o número da conta: ");
        int numeroConta = entradaInteiro();

        System.out.print("Digite o valor ");
        double valorDeposito = entradaDouble();

        ClientDAO.addAmount(numeroConta, valorDeposito);

    }

    private static void pressEnterToContinue(){
        System.out.println("Pressione Enter duas vezes para continuar...");
        try
        {
            System.in.read();
            input.nextLine();
        } catch(Exception e)
        {
            System.out.println("Algo deu errado!");
        }
    }

    public static void limpaConsole() {//Limpa a tela no windows, no linux e no MacOS
        try {
            if (System.getProperty("os.name").contains("Windows")) //verifica se o SO é windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                System.out.print("\033\143"); //Limpa console em MacOS e Linux
        } catch (IOException | InterruptedException e){
            System.out.println("Erro ao limpar o console!");
        }
    }

}

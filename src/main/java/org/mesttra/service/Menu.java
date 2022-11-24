package org.mesttra.service;

import java.io.IOException;
import java.util.*;

public class Menu{

    Scanner input = new Scanner(System.in);

    List<Cliente> listaCliente = new ArrayList<>();

    public int inicio() {
        System.out.println("==== Menu Principal ====");
        System.out.println();
        System.out.println("1 - Cadastrar Cliente");
        System.out.println("2 - Remover Cliente");
        System.out.println("3 - Consultar Cliente");
        System.out.println("4 - Alterar Limite");
        System.out.println("5 - Transferir");
        System.out.println("6 - Depositar");
        System.out.println("7 - Relatório de Clientes");
        System.out.println("8 - Sacar");
        System.out.println("9 - Sair");
        System.out.println();
        System.out.println("==========================");

        System.out.println();
        System.out.print("Digite a opção desejada: ");
        int entrada = entradaInteiro();

        opcaoEscolhida(entrada);

        return entrada;

    }

    private void opcaoEscolhida(int entrada) {

        switch (entrada){
            case 1:
                cadastrarCliente();
                break;
            case 2:
                removerCliente();
                break;
            case 3:
                consultarCliente();
                break;
            case 4:
                alterarLimite();
                break;
            case 5:
                transferir();
                break;
            case 6:
                depositar();
                break;
            case 7:
                listarClientes();
                break;
            case 8:
                sacar();
                break;
            case 9:
                System.out.println("Obrigado por utilizar nosso sistema!");
                break;
            default:
                System.out.println("Opção inserida inválida!");

        }
        pressEnterToContinue();
        limpaConsole();


    }

    private String entradaString(){

        String entrada;

        try{
            return entrada = input.nextLine();
        } catch(NullPointerException e){
            System.out.println("Valor inserido não pode ser nulo!");
            e.printStackTrace();
        }

        return null;
    }

    private double entradaDouble(){

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

    private int entradaInteiro() {

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
    public void cadastrarCliente() {

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

    private int getTipoCliente() {
        System.out.println("Selecione uma opção: ");
        System.out.println("1 - Pessoa Física");
        System.out.println("2 - Pessoa Jurídica");
        System.out.println("============================");

        System.out.println();
        System.out.print("Digite a opção desejada: ");
        return entradaInteiro();
    }

    public void cadastrarClientePF() {
        System.out.println("===== Cadastro Cliente Pessoa Física =====");

        System.out.println("Digite abaixo os dados, conforme solicitado: ");

        System.out.print("CPF: ");
        String cpf = entradaString();

        System.out.print("Nome: ");
        String nome = entradaString();

        System.out.print("Idade: ");
        int idade = entradaInteiro();

        System.out.print("Telefone: ");
        String telefone = entradaString();

        System.out.print("Limite de cheque especial: ");
        double limiteChequeEspecial = entradaDouble();

        adicionaClienteLista(new ClientePF(cpf, nome, idade, telefone, limiteChequeEspecial) {
        });

        System.out.println("Cliente cadastrado com sucesso!");
    }

    public void adicionaClienteLista(Cliente novoCliente){

        for (int i=0; i<listaCliente.length; i++) {
            if(listaCliente[i]==null){
                listaCliente[i] = novoCliente;
                break;
            }
        }
    }

    public void cadastrarClientePJ() {
        System.out.println("===== Cadastro Cliente Pessoa Jurídica =====");

        System.out.println("Digite abaixo os dados, conforme solicitado: ");

        System.out.print("CNPJ: ");
        String cnpj = entradaString();

        System.out.print("Digite o número de Sócios: ");
        int numeroSocio = entradaInteiro();

        String[] nomeSocio = new String[numeroSocio];

        for(int i = 0; i<numeroSocio; i++){
            System.out.print("Digite o nome do sócio n° " + (i+1) + " : ");
            nomeSocio[i] = entradaString();
        }

        System.out.print("Nome social: ");
        String nomeSocial = entradaString();

        System.out.print("Nome fantasia: ");
        String nomeFantasia = entradaString();

        System.out.print("Telefone: ");
        String telefone = entradaString();

        System.out.print("Limite de cheque especial: ");
        double limiteChequeEspecial = entradaDouble();

        adicionaClienteLista(new ClientePJ(cnpj, nomeSocio, nomeSocial, nomeFantasia, telefone, limiteChequeEspecial));

        System.out.println("Cliente cadastrado com sucesso!");
    }

    public void removerCliente(){
        System.out.println("==== Remover Cliente ====");
        int tipoCliente  = getTipoCliente();

        String opcaoCliente;

        if(tipoCliente == 1){
            opcaoCliente = "PF";
        } else{
            opcaoCliente = "PJ";
        }

        System.out.print("Digite o número da conta: ");
        int numeroConta = entradaInteiro();
        boolean clienteRemovido = false;

        for(int i= 0; i<listaCliente.length; i++){
            if((listaCliente[i] != null) && (listaCliente[i].getNumeroConta() == numeroConta && listaCliente[i].getTipoConta().equalsIgnoreCase(opcaoCliente))){
                listaCliente[i]=null;
                System.out.println("Cliente removido com sucesso!");
                clienteRemovido = true;
                break;
            }
        }
        if(!clienteRemovido) System.out.println("Cliente não existe!");
    }

    public void consultarCliente(){

        System.out.println("===== Consulta de cliente ====");
        Cliente cliente = retornaCliente();

        if(cliente!=null){
            System.out.println(cliente.toString());
        } else {
            System.out.println("Cliente não existe!");
        }
    }

    private Cliente retornaCliente() {
        int tipoCliente  = getTipoCliente();

        String opcaoCliente;

        if(tipoCliente == 1){
            opcaoCliente = "PF";
        } else{
            opcaoCliente = "PJ";
        }
        System.out.print("Digite o número da conta do cliente: ");
        int numeroConta = entradaInteiro();

        if(numeroContaExiste(numeroConta, opcaoCliente)){
            return buscaCliente(numeroConta, opcaoCliente);
        }
        return null;
    }

    private Cliente buscaCliente(int numeroConta, String tipoCliente){

        for (Cliente cliente : listaCliente) {

            if (cliente != null && cliente.getNumeroConta() == numeroConta && cliente.getTipoConta().equalsIgnoreCase(tipoCliente)) {
                return cliente;
            }

        }
        return null;
    }

    private boolean numeroContaExiste(int numeroConta, String tipoCliente){

        for (Cliente cliente : listaCliente) {

            if (cliente != null && cliente.getNumeroConta() == numeroConta && cliente.getTipoConta().equalsIgnoreCase(tipoCliente)) {
                return true;
            }
        }
        return false;
    }

    public void alterarLimite(){
        System.out.println("==== Alterar limite cheque especial ====");
        Cliente cliente = retornaCliente();

        if(cliente != null) {
            System.out.print("Digite o novo limite de cheque especial: ");
            double novoLimite = entradaDouble();

            if(cliente.getSaldo()<0 && Math.abs(cliente.getSaldo()) > cliente.getLimiteChequeEspecial()){
                System.out.println("O valor em uso do cheque especial é superior ao novo limite");
                cliente = null;
            }

            while (novoLimite<0){
                System.out.println("Insira um valor positivo!");
                System.out.print("Digite o novo limite de cheque especial: ");
                novoLimite = entradaDouble();
            }
            cliente.setLimiteChequeEspecial(novoLimite);
            System.out.println("Limite alterado com sucesso!");
        } else {
            System.out.println("Transação cancelada!");
        }
    }

    public void transferir(){
        System.out.println("==== Transferência ====");

        System.out.println("Conta de origem: ");
        Cliente origem = retornaCliente();

        System.out.println("Conta de destino: ");
        Cliente destino  = retornaCliente();

        boolean contaExiste = origem!=null && destino!=null;

        if(contaExiste && ( origem.getNumeroConta() == destino.getNumeroConta()) && origem.getTipoConta().equalsIgnoreCase(destino.getTipoConta())){
            System.out.println("As contas precisam ser diferentes!");
            contaExiste = false;
        }

        double valorTransferencia =0;

        if(contaExiste){
            System.out.print("Valor a ser transferido: ");
            valorTransferencia = entradaDouble();
        }


        if(contaExiste && origem.getSaldo() + origem.getLimiteChequeEspecial() < valorTransferencia){
            System.out.println("Saldo insuficiente!");
        } else if(contaExiste){

            origem.setSaldo(origem.getSaldo()-valorTransferencia);

            destino.setSaldo(destino.getSaldo()+valorTransferencia);

            System.out.println("Transferência concluída!");
        } else {
            System.out.println("Transação cancelada!");
        }
    }

    public void depositar(){
        System.out.println("==== Deposito ====");
        Cliente cliente = retornaCliente();

        if(cliente != null){
            System.out.print("Valor a ser depositado: ");
            double valorDeposito = entradaDouble();

            if(valorDeposito<0){
                System.out.println("Valor inserido inválido!");
                cliente = null;
            }
            cliente.setSaldo(cliente.getSaldo() + valorDeposito);

            System.out.println("Deposito efetuado!");
        } else {
            System.out.println("Transação cancelada!");
        }
    }

    public void listarClientes(){

        System.out.println("==== Lista de Clientes ====");

        Arrays.sort(listaCliente, new comparatorTipoNumeroConta());

        for(Cliente cliente :
                listaCliente) {
            if(cliente!=null) {
                System.out.println("Cliente " + cliente.getTipoConta() + " " + cliente.numeroConta + ":" );
                System.out.println(cliente.toString());

            }
        }
    }

    public void sacar(){
        System.out.println("==== Sacar ====");

        System.out.println("Conta do cliente: ");
        Cliente cliente = retornaCliente();

        boolean contaExiste = cliente!=null;

        double valorSaque=0;

        if(contaExiste) {
            System.out.print("Valor a ser sacado: ");
            valorSaque = entradaDouble();
        }

        if(contaExiste && cliente.getSaldo() + cliente.getLimiteChequeEspecial() < valorSaque){
            System.out.println("Saldo insuficiente!");
        } else if(contaExiste){

            cliente.setSaldo(cliente.getSaldo()-valorSaque);

            System.out.println("Saque concluído!");
        } else {
            System.out.println("Transação cancelada!");
        }
    }

    private void pressEnterToContinue(){
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

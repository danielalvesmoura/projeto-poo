package main;



import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

public class JavaFX extends Application {

    private Stage stage;

    int x = 1200;
    int y = 700;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.stage = primaryStage;
        stage.setTitle("Portal X");

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/secaoDashboard.fxml"));

        Scene scene = new Scene(root);

        URL css = getClass().getResource("/fxml/style.css");
        if(css!=null) scene.getStylesheets().add(css.toExternalForm());
        else System.out.println("nao funfou");

        //scene.getStylesheets().add(getClass().getResource("/fxml/style.css").toExternalForm());
        stage.setScene(scene);

        stage.setFullScreen(true);

        stage.show();
    }

    private void menuInicial() {
        Label titulo = new Label("Portal X");
        titulo.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        HBox topBar = new HBox(titulo);
        topBar.setStyle("-fx-background-color: #2c3e50; -fx-padding: 15;");

        topBar.setAlignment(Pos.CENTER_LEFT);

        Button btnCadastro = new Button("Cadastros");
        btnCadastro.setMaxWidth(Double.MAX_VALUE);
        btnCadastro.setOnAction(e -> menuCadastro());

        VBox sideBar = new VBox(10, btnCadastro);
        sideBar.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 10;");
        sideBar.setPrefWidth(150);

        Label mensagem = new Label("Bem-vindo ao sistema!");
        mensagem.setStyle("-fx-font-size: 16px;");

        StackPane centro = new StackPane(mensagem);
        centro.setPadding(new Insets(20));

        BorderPane layout = new BorderPane();
        layout.setTop(topBar);
        layout.setLeft(sideBar);
        layout.setCenter(centro);

        Scene cena = new Scene(layout, 1200, 700);
        stage.setScene(cena);
    }




    private void menuCadastro() {
        HBox topBar = barraSuperiorCadastro();

        VBox sideBar = new VBox(10);
        sideBar.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 10;");
        sideBar.setPrefWidth(150);

        criaBotoesCadastro(sideBar);

        BorderPane layout = criaLayoutCadastro(topBar, sideBar);

        stage.setScene(new Scene(layout, x, y));
    }

    public BorderPane criaLayoutCadastro(HBox topBar, VBox sideBar) {
        Label msg = new Label("Escolha uma opção de cadastro.");
        msg.setStyle("-fx-font-size: 16px;");
        StackPane centro = new StackPane(msg);
        centro.setPadding(new Insets(20));

        BorderPane layout = new BorderPane();
        layout.setTop(topBar);
        layout.setLeft(sideBar);
        layout.setCenter(centro);

        return layout;
    }

    public HBox barraSuperiorCadastro() {
        Label titulo = new Label("Cadastros");
        titulo.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");
        HBox topBar = new HBox(titulo);
        topBar.setStyle("-fx-background-color: #2c3e50; -fx-padding: 15;");
        topBar.setAlignment(Pos.CENTER_LEFT);

        return topBar;
    }

    public void criaBotoesCadastro(VBox sideBar) {
        Button btnEvento = new Button("Evento");
        Button btnSala = new Button("Sala");
        Button btnPalestrante = new Button("Palestrante");
        Button btnSessao = new Button("Sessão");

        Button btnVoltarInicial = new Button("Voltar");
        btnVoltarInicial.setOnAction(e -> menuInicial());

        for (Button btn : new Button[]{btnEvento, btnSala, btnPalestrante, btnSessao}) {
            btn.setMaxWidth(Double.MAX_VALUE);
        }
        btnVoltarInicial.setMaxWidth(Double.MAX_VALUE);

        btnEvento.setOnAction(e -> mostrarCadastro("Evento"));
        btnSala.setOnAction(e -> mostrarCadastro("Sala"));
        btnPalestrante.setOnAction(e -> mostrarCadastro("Palestrante"));
        btnSessao.setOnAction(e -> mostrarCadastro("Sessão"));

        sideBar.getChildren().addAll(btnEvento, btnSala, btnPalestrante, btnSessao,btnVoltarInicial);
    }




    private void mostrarCadastro(String tipoCadastro) {
        HBox topBar = barraSuperior(tipoCadastro);
        VBox sideBar = barraLateral(tipoCadastro);

        Button btnSalvar = new Button("Salvar");

        VBox centro = mudaTela(tipoCadastro, btnSalvar);

        btnSalvar.setOnAction(e -> salvarCadastro(tipoCadastro, centro));

        BorderPane layout = criaLayout(topBar, sideBar, centro);

        stage.setScene(new Scene(layout, x, y));
    }

    public void salvarCadastro(String tipoCadastro, VBox centro) {
        if(tipoCadastro.equals("Evento")) {

        }
    }

    public BorderPane criaLayout(HBox topBar, VBox sideBar, VBox centro) {
        BorderPane layout = new BorderPane();
        layout.setTop(topBar);
        layout.setLeft(sideBar);
        layout.setCenter(centro);

        return layout;
    }

    public VBox mudaTela(String tipoCadastro, Button btnSalvar) {
        VBox centro;

        if(tipoCadastro.equals("Evento")) {
            centro = camposEvento(btnSalvar, tipoCadastro);
        } else if(tipoCadastro.equals("Sala")) {
            centro = camposSala(btnSalvar, tipoCadastro);
        } else if(tipoCadastro.equals("Palestrante")) {
            centro = camposPalestrante(btnSalvar, tipoCadastro);
        } else {
            centro = camposSessao(btnSalvar, tipoCadastro);
        }

        centro.setAlignment(Pos.CENTER_LEFT);
        centro.setPadding(new Insets(20));

        return centro;
    }

    public HBox barraSuperior(String tipoCadastro) {
        Label titulo = new Label("Cadastro de " + tipoCadastro);
        titulo.setStyle("-fx-font-size: 20px; -fx-text-fill: white;");

        HBox topBar = new HBox(titulo);
        topBar.setStyle("-fx-background-color: #2c3e50; -fx-padding: 15;");
        topBar.setAlignment(Pos.CENTER_LEFT);

        return topBar;
    }

    public VBox barraLateral(String tipoCadastro) {
        VBox sideBar = new VBox(10);
        sideBar.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 10;");
        sideBar.setPrefWidth(150);

        Button btnVoltar = new Button("Voltar");
        btnVoltar.setMaxWidth(Double.MAX_VALUE);
        btnVoltar.setOnAction(e -> menuCadastro());
        sideBar.getChildren().add(btnVoltar);

        return sideBar;
    }



    public VBox camposEvento(Button btnSalvar, String tipoCadastro) {
        Label nome = new Label("Nome:");
        TextField campoNome = new TextField();
        campoNome.setPromptText("Digite o nome");

        Label descricao = new Label("Descrição:");
        TextField campoDescricao = new TextField();
        campoDescricao.setPromptText("Digite a descrição");

        Label endereco = new Label("Endereço:");
        TextField campoEndereco = new TextField();
        campoEndereco.setPromptText("Digite o endereço");

        Label dataInicio = new Label("Data de início:");
        DatePicker datePickerInicio = new DatePicker();
        LocalDate dataInicioSelecionada = datePickerInicio.getValue();

        Label dataFim = new Label("Data de início:");
        DatePicker datePickerFim = new DatePicker();
        LocalDate dataFimSelecionada = datePickerFim.getValue();

        return new VBox(15, nome, campoNome, descricao, campoDescricao, endereco, campoEndereco, dataInicio, datePickerInicio, dataFim, datePickerFim, btnSalvar);
    }

    public VBox camposSala(Button btnSalvar, String tipoCadastro) {
        Label nome = new Label("Nome:");
        TextField campoNome = new TextField();
        campoNome.setPromptText("Digite o nome");

        Label capacidade = new Label("Capacidade:");
        TextField campoCapacidade = new TextField();
        campoCapacidade.setPromptText("Digite a capacidade");

        Label localizacao = new Label("Localização:");
        TextField campoLocalizacao = new TextField();
        campoLocalizacao.setPromptText("Digite a localização");

        return new VBox(15, nome, campoNome, capacidade, campoCapacidade, localizacao, campoLocalizacao, btnSalvar);
    }

    public VBox camposPalestrante(Button btnSalvar, String tipoCadastro) {
        Label nome = new Label("Nome:");
        TextField campoNome = new TextField();
        campoNome.setPromptText("Digite o nome completo");

        Label email = new Label("Email:");
        TextField campoEmail = new TextField();
        campoEmail.setPromptText("Digite o email");

        Label telefone = new Label("Telefone:");
        TextField campoTelefone = new TextField();
        campoTelefone.setPromptText("Digite o telefone");

        Label biografia = new Label("Biografia:");
        TextField campoBiografia = new TextField();
        campoBiografia.setPromptText("Digite a biografia");

        Label especialidade = new Label("Especialidade:");
        TextField campoEspecialidade = new TextField();
        campoEspecialidade.setPromptText("Digite a especialiade");

        Label curriculo = new Label("Telefone:");
        TextField campoCurriculo = new TextField();
        campoCurriculo.setPromptText("Digite o curriculo");

        return new VBox(15, nome, campoNome, email, campoEmail, telefone, campoTelefone, biografia, campoBiografia, especialidade, campoEspecialidade, curriculo, campoCurriculo, btnSalvar);
    }

    public VBox camposSessao(Button btnSalvar, String tipoCadastro) {
        Label titulo = new Label("Título:");
        TextField campoTitulo = new TextField();
        campoTitulo.setPromptText("Digite o nome");

        Label descricao = new Label("Descrição:");
        TextField campoDescricao = new TextField();
        campoDescricao.setPromptText("Digite a descrição");

        ToggleGroup tipos = new ToggleGroup();

        Label tipo = new Label("Tipo:");

        RadioButton palestra = new RadioButton("Palestra");
        palestra.setToggleGroup(tipos);

        RadioButton painel = new RadioButton("Painel");
        painel.setToggleGroup(tipos);

        RadioButton treinamento = new RadioButton("Treinamento");
        treinamento.setToggleGroup(tipos);

        Label resultado = new Label("Selecione uma opção");

        tipos.selectedToggleProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                RadioButton selecionado = (RadioButton) tipos.getSelectedToggle();
                resultado.setText("Selecionado: " + selecionado.getText());
            }
        });

        return new VBox(15, titulo, campoTitulo, descricao, campoDescricao, tipo, palestra, painel, treinamento, btnSalvar);
    }

}

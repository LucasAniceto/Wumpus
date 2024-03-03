package com.aniceto;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Menu Inicial");

        Label mensagemLabel1 = new Label ("Bem vindo ao Mundo de Lupus! ");
        Label mensagemLabel2 = new Label (" Seu objetivo deve ser conquistar o ouro e sair vivo!");
        Label mensagemLabel4 = new Label (" Voce possui um arco e uma lanterna de inicio!");
        Label mensagemLabel3 = new Label ("SETINHAS se movem\n WASD usa o arco e flecha\n IJKL usa a lanterna\n ENTER cria flechas com madeira");
        mensagemLabel1.setStyle("-fx-text-fill: white; -fx-font-size: 14pt;");
        mensagemLabel2.setStyle("-fx-text-fill: white; -fx-font-size: 14pt;");
        mensagemLabel4.setStyle("-fx-text-fill: white; -fx-font-size: 12pt;");
        mensagemLabel3.setStyle("-fx-text-fill: white; -fx-font-size: 12pt;");


        // Criação dos botões
        Button jogarButton = criarBotao("Jogar");
        Button sairButton = criarBotao("Sair");

        // Ação ao clicar no botão "Jogar"
        jogarButton.setOnAction(e -> {
            primaryStage.close();
            Jogar jogar = new Jogar();
            jogar.start(primaryStage);
        });

        // Ação ao clicar no botão "Sair"
        sairButton.setOnAction(e -> primaryStage.close());

        // Layout do menu inicial
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(mensagemLabel1, mensagemLabel2, mensagemLabel4, mensagemLabel3, jogarButton, sairButton);

        BackgroundFill backgroundFill = new BackgroundFill(Color.DARKGREEN, null, null);
        Background background = new Background(backgroundFill);
        layout.setBackground(background);

        Scene scene = new Scene(layout, 450, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button criarBotao(String texto) {
        Button botao = new Button(texto);
        botao.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14pt;");
        return botao;
    }
}

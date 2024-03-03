package com.aniceto;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Label;


public class TelaFinal extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    Label mensagemLabel;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Menu Final");
        mensagemLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14pt;");

        Button recomecarButton = criarBotao("Novo Jogo");
        Button sairButton = criarBotao("Sair");

        recomecarButton.setOnAction(e -> {
            primaryStage.close();
            Jogar jogar = new Jogar();
            jogar.start(primaryStage);
        });

        sairButton.setOnAction(e -> primaryStage.close());

        VBox layout = new VBox(20);  // Layout do menu final
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(mensagemLabel, recomecarButton, sairButton);

        BackgroundFill backgroundFill = new BackgroundFill(Color.DARKGREEN, null, null);
        Background background = new Background(backgroundFill);
        layout.setBackground(background);

        Scene scene = new Scene(layout, 450, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void ganhou(Stage primaryStage){
        mensagemLabel = new Label("Você ganhou!");
        start(primaryStage);
    }

    public void perdeu(Stage primaryStage){
        mensagemLabel = new Label("Você perdeu!\n Caiu em um poco!");
        start(primaryStage);
    }

    public void perdeuWUMPUS(Stage primaryStage) {
        mensagemLabel = new Label("Você perdeu!\n O Wumpus te comeu!");
        start(primaryStage);
    }
    public void perdeuSPIDER(Stage primaryStage) {
        mensagemLabel = new Label("Você perdeu!\n A aranha te comeu!");
        start(primaryStage);
    }

    private Button criarBotao(String texto) {
        Button botao = new Button(texto);
        botao.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14pt;");
        return botao;
    }
}

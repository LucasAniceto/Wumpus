package com.aniceto;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Jogar extends Application{

    private Stage primaryStage;
    private String path = "E:/Wpoo/WumpusPOO/src/main/java/com/aniceto";
    private Image playerImage;
    Cave cave = new Cave();
    private int mouseX, mouseY;
    private int currentlySelectedTile = -1;// significa que nenhum tile ta selecionado
    Player player = new Player(cave);
    ArrayList<String> input = new ArrayList<String>();//armazena as teclas pressionadas



    @Override
    public void start(Stage primarystage) {
        this.primaryStage = primarystage;
        primarystage.setTitle("Wumpus World");
        Group root = new Group();
        Scene scene = new Scene(root);
        primarystage.setScene(scene);

        Canvas canvas = new Canvas(1100, 900);
        root.getChildren().add(canvas);
        final GraphicsContext gc = canvas.getGraphicsContext2D();



        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                String code = keyEvent.getCode().toString();
                if(!input.contains(code)){
                    input.add(code);

                }

            }
        });

        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseX = (int)event.getX();
                mouseY = (int)event.getY();

                //clicou no pit na toolbar da direita
                if(mouseX >= 850 && mouseX <= 900 && mouseY >= 80 && mouseY <= 130){
                    currentlySelectedTile = Cave.PIT;
                }
                else if(mouseX >= 850 && mouseX <= 900 && mouseY >= 130 && mouseY <= 180){
                    currentlySelectedTile = Cave.SPIDER;
                }
                else if(mouseX >= 850 && mouseX <= 900 && mouseY >= 180 && mouseY <= 230){
                    currentlySelectedTile = Cave.WUMPUS;
                }
                else if(mouseX >= 850 && mouseX <= 900 && mouseY >= 230 && mouseY <= 280){
                    currentlySelectedTile = Cave.GOLD;
                }
                else if(mouseX >= 850 && mouseX <= 900 && mouseY >= 280 && mouseY <= 330){
                    currentlySelectedTile = Cave.GROUND;
                }
                else if(mouseX >= 850 && mouseX <= 900 && mouseY >= 20 && mouseY <= 80){
                    currentlySelectedTile = Cave.WOOD;
                }
                else if(event.getX() >= 850 && event.getX() <= 900 && event.getY() >= 330 && event.getY() <= 380) {
                    cave.clearAllFog();
                }
                if(currentlySelectedTile == cave.WOOD) {//tem um tile selecionado
                    Location clickLoc = convertClickToLocation(mouseX, mouseY);
                    System.out.println(clickLoc);
                    if(cave.isValid(clickLoc)) {//clicou na caverna
                        cave.setTileW(clickLoc, currentlySelectedTile);
                        //esvaziar a tile selecionada
                        currentlySelectedTile = -1;
                    }
                }



                //se clicarem na caverna e tem um tile selecionado do toolbar
                if(currentlySelectedTile != -1) {//tem um tile selecionado
                    Location clickLoc = convertClickToLocation(mouseX, mouseY);
                    System.out.println(clickLoc);
                    if(cave.isValid(clickLoc)) {//clicou na caverna
                        cave.setTile(clickLoc, currentlySelectedTile);
                        //esvaziar a tile selecionada
                        currentlySelectedTile = -1;
                    }
                }
                if(currentlySelectedTile == cave.WOOD) {//tem um tile selecionado
                    Location clickLoc = convertClickToLocation(mouseX, mouseY);
                    System.out.println(clickLoc);
                    if(cave.isValid(clickLoc)) {//clicou na caverna
                        cave.setTileW(clickLoc, currentlySelectedTile);
                        //esvaziar a tile selecionada
                        currentlySelectedTile = -1;
                    }
                }
            }
        });

        scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseX = (int)event.getX();
                mouseY = (int)event.getY();

            }
        });

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                //limpa a tela
                gc.setFill(Color.DARKGREEN);
                gc.fillRect(0, 0, 1100, 900);

                processInput();

                cave.drawn(gc);
                drawnToolBar(gc);
                cave.drawn1(gc);
                player.draw(gc);
            }
        }.start();

        primarystage.show();

    }

    public void processInput(){

        if (player.isPlayerAtStartLocation(player) && player.ouroColetado > 0){
            TelaFinal telaFinalizacao = new TelaFinal();
            telaFinalizacao.ganhou(new Stage());
            primaryStage.close();
            player.ouroColetado = 0;
            return;
        }

        //passa pela arraylist que tem tudo pressionado
        for(int i=0; i < input.size(); i++) {
            if (input.get(i).equals("I")) {
                if (player.getcargas() > 0) {
                    player.usarLanterna();
                    Location PlayerLoc = player.getLocation();
                    for (int l = 0; l < 15; l++) {
                        for (int j = 0; j < 15; j++) {
                            if (l < player.getLocation().getRow() && j == player.getLocation().getCol()) {
                                Location locationToClear = new Location(l, PlayerLoc.getCol());
                                cave.clearFogAtLocation(locationToClear);
                            }
                        }

                    }
                }
            }
            if (input.get(i).equals("K")) {
                if (player.getcargas() > 0) {
                    player.usarLanterna();
                    Location PlayerLoc = player.getLocation();
                    for (int l = 0; l < 15; l++) {
                        for (int j = 0; j < 15; j++) {
                            if (l > player.getLocation().getRow() && j == player.getLocation().getCol()) {
                                Location locationToClear = new Location(l, PlayerLoc.getCol());
                                cave.clearFogAtLocation(locationToClear);
                            }
                        }

                    }
                }
            }
            if (input.get(i).equals("J")) {
                if (player.getcargas() > 0) {
                    player.usarLanterna();
                    Location PlayerLoc = player.getLocation();
                    for (int l = 0; l < 15; l++) {
                        for (int j = 0; j < 15; j++) {
                            if (l == player.getLocation().getRow() && j < player.getLocation().getCol()) {
                                Location locationToClear = new Location(PlayerLoc.getRow(), j);
                                cave.clearFogAtLocation(locationToClear);
                            }
                        }

                    }
                }
            }
            if (input.get(i).equals("L")) {
                if (player.getcargas() > 0) {
                    player.usarLanterna();
                    Location PlayerLoc = player.getLocation();
                    for (int l = 0; l < 15; l++) {
                        for (int j = 0; j < 15; j++) {
                            if (l == player.getLocation().getRow() && j > player.getLocation().getCol()) {
                                Location locationToClear = new Location(PlayerLoc.getRow(), j);
                                cave.clearFogAtLocation(locationToClear);

                            }
                        }

                    }
                }
            }


            if (input.get(i).equals("W")) {
                if (player.quantidadeFlechas() <= 0) continue;
                Location PlayerLoc = player.getLocation();
                Location tile = new Location(PlayerLoc.getRow() - 1, PlayerLoc.getCol());
                cave.setTile(tile, cave.GROUND);
                player.removeFlecha();
            }
            if (input.get(i).equals("A")) {
                if (player.quantidadeFlechas() <= 0) continue;
                Location PlayerLoc = player.getLocation();
                Location tile = new Location(PlayerLoc.getRow(), PlayerLoc.getCol() - 1);
                cave.setTile(tile, cave.GROUND);
                player.removeFlecha();
            }
            if (input.get(i).equals("S")) {
                if (player.quantidadeFlechas() <= 0) continue;
                Location PlayerLoc = player.getLocation();
                Location tile = new Location(PlayerLoc.getRow() + 1, PlayerLoc.getCol());
                cave.setTile(tile, cave.GROUND);
                player.removeFlecha();
            }
            if (input.get(i).equals("D")) {
                if (player.quantidadeFlechas() <= 0) continue;
                Location PlayerLoc = player.getLocation();
                Location tile = new Location(PlayerLoc.getRow(), PlayerLoc.getCol() + 1);
                cave.setTile(tile, cave.GROUND);
                player.removeFlecha();
            }
            if (input.get(i).equals("ENTER")) {
                player.criarFlecha();
            }

            if (input.get(i).equals("RIGHT")) {
                player.moveRight();
                cave.updateFog(player);

                if (player.isOnPit(cave) && player.madeiraColetada < 1) {
                    player.subtraiVida(100);
                    primaryStage.close();
                    TelaFinal telaFinalizacao = new TelaFinal();
                    telaFinalizacao.perdeu(new Stage());
                    System.out.println("Voce caiu em um poco!");
                } else if ((player.isOnPit(cave) && player.madeiraColetada > 0)) {
                    System.out.println("Voce tampou um poco!");
                    player.perdeMadeira();
                    cave.setTile(player.getLocation(), cave.POCOFECHADO);
                }
                if (player.isOnGold(cave) && player.getTamanhoMochila() < 3) {
                    player.coletarOuro();
                    cave.setTile(player.getLocation(), Cave.GROUND);
                    System.out.println("Você coletou o ouro!");
                } else if (player.isOnGold(cave) && player.getTamanhoMochila() == 3) {
                    System.out.println("Sua Mochila Esta Cheia!");
                }
                if (player.isOnWood(cave) && player.getTamanhoMochila() < 3) {
                    player.coletarMadeira();
                    cave.setTile(player.getLocation(), Cave.GROUND);
                    System.out.println("Voce coletou uma Madeira!");
                } else if (player.isOnWood(cave) && player.getTamanhoMochila() == 3) {
                    System.out.println("Sua Mochila Esta Cheia!");
                }
                if (player.isOnWumpus(cave)) {
                    player.subtraiVida(100);
                    System.out.println("O wumpus te matou!");
                }
                if (player.isOnSpider(cave)) {
                    player.subtraiVida(50);

                    if(player.isOnSpider(cave) && player.getEnergiaVital() < 1){primaryStage.close();
                    TelaFinal telaFinalizacao = new TelaFinal();
                    telaFinalizacao.perdeuSPIDER(new Stage());
                    System.out.println("Uma aranha te mordeu!");}
                }
                input.remove(i);
                i--;//decrementa i sempre que deletar algo da lista
                cave.moverMonstro1(cave.getLocMonstro1());
                cave.moverMonstro2(cave.getLocMonstro2());
                cave.moverMonstro2(cave.getLocMonstro2());

                if (player.isOnWumpus(cave)) {
                    player.subtraiVida(100);
                    primaryStage.close();
                    TelaFinal telaFinalizacao = new TelaFinal();
                    telaFinalizacao.perdeuWUMPUS(new Stage());
                    System.out.println("O wumpus te matou!");
                }
                if (player.isOnSpider(cave)) {
                    player.subtraiVida(50);
                    System.out.println("Uma aranha te mordeu!");
                    player.mostrarMochila();
                }
            } else if (input.get(i).equals("LEFT")) {
                player.moveLeft();
                cave.updateFog(player);
                if (player.isOnPit(cave) && player.madeiraColetada < 1) {
                    player.subtraiVida(100);
                    primaryStage.close();
                    TelaFinal telaFinalizacao = new TelaFinal();
                    telaFinalizacao.perdeu(new Stage());
                    System.out.println("Voce caiu em um poco!");
                } else if ((player.isOnPit(cave) && player.madeiraColetada > 0)) {
                    System.out.println("Voce tampou um poco!");
                    player.perdeMadeira();
                    cave.setTile(player.getLocation(), cave.POCOFECHADO);
                }
                if (player.isOnGold(cave) && player.getTamanhoMochila() < 3) {
                    player.coletarOuro();
                    cave.setTile(player.getLocation(), Cave.GROUND);
                    System.out.println("Você coletou o ouro!");
                } else if (player.isOnGold(cave) && player.getTamanhoMochila() == 3) {
                    System.out.println("Sua Mochila Esta Cheia!");
                }
                if (player.isOnWood(cave) && player.getTamanhoMochila() < 3) {
                    player.coletarMadeira();
                    cave.setTile(player.getLocation(), Cave.GROUND);
                    System.out.println("Voce coletou uma Madeira!");
                } else if (player.isOnWood(cave) && player.getTamanhoMochila() == 3) {
                    System.out.println("Sua Mochila Esta Cheia!");
                }
                if (player.isOnWumpus(cave)) {
                    player.subtraiVida(100);
                    System.out.println("O wumpus te matou!");
                }
                if (player.isOnSpider(cave)) {
                    player.subtraiVida(50);

                    if(player.isOnSpider(cave) && player.getEnergiaVital() < 1){primaryStage.close();
                        TelaFinal telaFinalizacao = new TelaFinal();
                        telaFinalizacao.perdeuSPIDER(new Stage());
                        System.out.println("Uma aranha te mordeu!");}
                }
                input.remove(i);
                i--;//decrementa i sempre que deletar algo da lista
                cave.moverMonstro1(cave.getLocMonstro1());
                cave.moverMonstro2(cave.getLocMonstro2());
                cave.moverMonstro2(cave.getLocMonstro2());
                if (player.isOnWumpus(cave)) {
                    player.subtraiVida(100);
                    primaryStage.close();
                    TelaFinal telaFinalizacao = new TelaFinal();
                    telaFinalizacao.perdeuWUMPUS(new Stage());
                    System.out.println("O wumpus te matou!");
                }
                if (player.isOnSpider(cave)) {
                    player.subtraiVida(50);
                    System.out.println("Uma aranha te mordeu!");
                    player.mostrarMochila();
                }
            } else if (input.get(i).equals("UP")) {
                player.moveUp();
                cave.updateFog(player);
                if (player.isOnPit(cave) && player.madeiraColetada < 1) {
                    player.subtraiVida(100);
                    primaryStage.close();
                    TelaFinal telaFinalizacao = new TelaFinal();
                    telaFinalizacao.perdeu(new Stage());
                    System.out.println("Voce caiu em um poco!");
                } else if ((player.isOnPit(cave) && player.madeiraColetada > 0)) {
                    System.out.println("Voce tampou um poco!");
                    player.perdeMadeira();
                    cave.setTile(player.getLocation(), cave.POCOFECHADO);
                }
                if (player.isOnGold(cave) && player.getTamanhoMochila() < 3) {
                    player.coletarOuro();
                    cave.setTile(player.getLocation(), Cave.GROUND);
                    System.out.println("Você coletou o ouro!");
                } else if (player.isOnGold(cave) && player.getTamanhoMochila() == 3) {
                    System.out.println("Sua Mochila Esta Cheia!");
                }
                if (player.isOnWood(cave) && player.getTamanhoMochila() < 3) {
                    player.coletarMadeira();
                    cave.setTile(player.getLocation(), Cave.GROUND);
                    System.out.println("Voce coletou uma Madeira!");
                } else if (player.isOnWood(cave) && player.getTamanhoMochila() == 3) {
                    System.out.println("Sua Mochila Esta Cheia!");
                }
                if (player.isOnWumpus(cave)) {
                    player.subtraiVida(100);
                    primaryStage.close();
                    TelaFinal telaFinalizacao = new TelaFinal();
                    telaFinalizacao.perdeuWUMPUS(new Stage());
                    System.out.println("O wumpus te matou!");
                }
                if (player.isOnSpider(cave)) {
                    player.subtraiVida(50);

                    if(player.isOnSpider(cave) && player.getEnergiaVital() < 1){primaryStage.close();
                        TelaFinal telaFinalizacao = new TelaFinal();
                        telaFinalizacao.perdeuSPIDER(new Stage());
                        System.out.println("Uma aranha te mordeu!");}
                }
                input.remove(i);
                i--;//decrementa i sempre que deletar algo da lista
                cave.moverMonstro1(cave.getLocMonstro1());
                cave.moverMonstro2(cave.getLocMonstro2());
                cave.moverMonstro2(cave.getLocMonstro2());
                if (player.isOnWumpus(cave)) {
                    player.subtraiVida(100);
                    System.out.println("O wumpus te matou!");
                }
                if (player.isOnSpider(cave)) {
                    player.subtraiVida(50);
                    System.out.println("Uma aranha te mordeu!");
                    player.mostrarMochila();
                }
            } else if (input.get(i).equals("DOWN")) {
                player.moveDown();
                cave.updateFog(player);
                if (player.isOnPit(cave) && player.madeiraColetada < 1) {
                    player.subtraiVida(100);
                    primaryStage.close();
                    TelaFinal telaFinalizacao = new TelaFinal();
                    telaFinalizacao.perdeuWUMPUS(new Stage());
                    System.out.println("Voce caiu em um poco!");
                } else if ((player.isOnPit(cave) && player.madeiraColetada > 0)) {
                    System.out.println("Voce tampou um poco!");
                    player.perdeMadeira();
                    cave.setTile(player.getLocation(), cave.POCOFECHADO);
                }
                if (player.isOnGold(cave) && player.getTamanhoMochila() < 3) {
                    player.coletarOuro();
                    cave.setTile(player.getLocation(), Cave.GROUND);
                    System.out.println("Você coletou o ouro!");
                } else if (player.isOnGold(cave) && player.getTamanhoMochila() == 3) {
                    System.out.println("Sua Mochila Esta Cheia!");
                }
                if (player.isOnWood(cave) && player.getTamanhoMochila() < 3) {
                    player.coletarMadeira();
                    cave.setTile(player.getLocation(), Cave.GROUND);
                    System.out.println("Voce coletou uma Madeira!");
                } else if (player.isOnWood(cave) && player.getTamanhoMochila() == 3) {
                    System.out.println("Sua Mochila Esta Cheia!");
                }
                if (player.isOnWumpus(cave)) {
                    player.subtraiVida(100);
                    primaryStage.close();
                    TelaFinal telaFinalizacao = new TelaFinal();
                    telaFinalizacao.perdeuWUMPUS(new Stage());
                    System.out.println("O wumpus te matou!");
                }
                if (player.isOnSpider(cave)) {
                    player.subtraiVida(50);

                    if(player.isOnSpider(cave) && player.getEnergiaVital() < 1){primaryStage.close();
                        TelaFinal telaFinalizacao = new TelaFinal();
                        telaFinalizacao.perdeuSPIDER(new Stage());
                        System.out.println("Uma aranha te mordeu!");}
                }
                input.remove(i);
                i--;//decrementa i sempre que deletar algo da lista
                cave.moverMonstro1(cave.getLocMonstro1());
                cave.moverMonstro2(cave.getLocMonstro2());
                cave.moverMonstro2(cave.getLocMonstro2());
                if (player.isOnWumpus(cave)) {
                    player.subtraiVida(100);
                    System.out.println("O wumpus te matou!");
                }
                if (player.isOnSpider(cave)) {
                    player.subtraiVida(50);
                    System.out.println("Uma aranha te mordeu!");
                    player.mostrarMochila();
                }
            } else {
                input.remove(i);
                i--;//remove se apertou algo q nao era pra ser apertado
            }
        }

    }


    public void drawnToolBar(GraphicsContext gc){
        gc.setFill(Color.BLACK);
        gc.setFill(Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font(14));
        gc.fillText("MENU DE CRIAÇAO", 850, 400);
        gc.drawImage(cave.getWoodImage(), 850, 30);
        gc.drawImage(cave.getPitImage(), 850, 80);
        gc.drawImage(cave.getSpiderImage(), 850, 130);
        gc.drawImage(cave.getWumpusImage(), 850, 180);
        gc.drawImage(cave.getGoldImage(), 850, 230);
        gc.drawImage(cave.getGroundImage(), 850, 280);
        gc.drawImage(cave.getGroundImage(), 850, 320);
        gc.fillText("DEBUGGAR", 910, 350);
        gc.drawImage(playerImage, 850, 330);
        gc.drawImage(cave.getMochilaImage(), 25, 775);
        gc.drawImage(cave.getMochilaImage(), 75, 775);
        gc.drawImage(cave.getMochilaImage(), 75, 825);
        gc.drawImage(cave.getMochilaImage(), 25, 825);
        gc.drawImage(cave.getMochilaImage(), 25, 875);
        gc.drawImage(cave.getMochilaImage(), 75, 875);
        gc.drawImage(cave.getMochilaImage(), 100, 825);
        gc.drawImage(cave.getMochilaImage(), 100, 875);
        gc.drawImage(cave.getMochilaImage(), 100, 775);
        gc.fillText("MOCHILA\n" + player.mostrarMochila(), 25, 790 );
        gc.fillText("VIDA " + player.getEnergiaVital() + "/100", 25, 870);
        gc.fillText("Usos Lanterna" + player.getcargas() + "/2" , 25, 890);







        if(currentlySelectedTile != -1) {
            if (currentlySelectedTile == Cave.PIT) {
                gc.drawImage(cave.getPitImage(), mouseX - 25, mouseY - 25);
            } else if (currentlySelectedTile == Cave.SPIDER) {
                gc.drawImage(cave.getSpiderImage(), mouseX - 25, mouseY - 25);
            }
            else if (currentlySelectedTile == Cave.WUMPUS) {
                gc.drawImage(cave.getWumpusImage(), mouseX - 25, mouseY - 25);
            }
            else if (currentlySelectedTile == Cave.GOLD) {
                gc.drawImage(cave.getGoldImage(), mouseX - 25, mouseY - 25);
            }
            else if (currentlySelectedTile == Cave.GROUND) {
                gc.drawImage(cave.getGroundImage(), mouseX - 25, mouseY - 25);
            }
            else if (currentlySelectedTile == Cave.WOOD) {
                gc.drawImage(cave.getWoodImage(), mouseX - 25, mouseY - 25);
            }
        }
    }
    public Location convertClickToLocation(int x, int y){
        int row = (y-Cave.xoffset)/50;
        int col = (x-Cave.yoffset)/50;

        Location loc = new Location(row, col);
        System.out.println(loc);
        return loc;
    }

    public static void main(String[] args) {launch(args);}
}

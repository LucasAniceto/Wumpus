package com.aniceto;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String path = "E:/Wpoo/WumpusPOO/src/main/java/com/aniceto";
    private Location loc;
    private int energiaVital = 100;
    private Image playerImage;
    private Cave cave;//player tem acesso direto a caverna

    public int ouroColetado = 0;

    public int madeiraColetada = 0;

    private List<Item> flechas;
    int cargasLanters = 2;

    private List<Item> mochila;
    Item madeira = new Item("Madeira");




    public Player(Cave cave){
        this.cave= cave;
        loc = new Location(14, 0);
        FileInputStream inputStream = null;
        mochila = new ArrayList<>();
        flechas = new ArrayList<>();

        try{
            inputStream = new FileInputStream(path + "/imagens/hero.png");
            playerImage = new Image(inputStream);

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }



    public void moveRight(){
        if(loc.getCol() < 14) {
            loc.setCol(loc.getCol()+1);
        }
    }
    public void moveLeft(){
        if(loc.getCol() > 0) {
            loc.setCol(loc.getCol() - 1);
        }
    }
    public void moveUp(){
        if(loc.getRow() > 0) {
            loc.setRow(loc.getRow() - 1);
        }
    }
    public void moveDown(){
        if(loc.getRow() < 14) {
            loc.setRow(loc.getRow() + 1);
        }
    }

    public void coletarOuro() {
        Item ouro = new Item("Ouro");
        adicionarItem(ouro);
        ouroColetado++;
    }
    public void coletarMadeira(){

        adicionarItem(madeira);
        madeiraColetada++;
    }
    public void perdeMadeira(){
        removeItem(madeira);
        madeiraColetada--;
    }
    public int getTamanhoMochila() {
        return mochila.size();
    }

    public boolean isPlayerAtStartLocation(Player player) {
        Location playerLocation = player.getLocation();
        return playerLocation.getRow() == 14 && playerLocation.getCol() == 0;
    }

    public Location getLocation() {
        return loc;
    }

    public void subtraiVida(int ataque) {
        this.energiaVital -= ataque;
        if (energiaVital <= 0) {
            // A energia vital chegou a zero ou menos, você pode executar a lógica de game over aqui
            gameOver();
        }
    }
    private void gameOver() {
        // Implemente a lógica de game over aqui, como exibir uma mensagem de fim de jogo e encerrar o jogo.
        System.out.println("Game Over!");
        // Você pode encerrar o jogo ou executar outras ações apropriadas.
    }

    public int getEnergiaVital(){
        return energiaVital;
    }





    // Dentro da classe Player



    public boolean isOnPit(Cave cave) {//ve se ta no poco
        Location playerLocation = getLocation();
        int tileType = cave.getTileType(playerLocation);
        return tileType == Cave.PIT;
    }

    public boolean isOnGold(Cave cave) {//ve se ta no ouro
        Location playerLocation = getLocation();
        int tileType = cave.getTileType(playerLocation);
        return tileType == Cave.GOLD;
    }

    public boolean isOnWood(Cave cave) {//ve se ta na madeira
        Location playerLocation = getLocation();
        int tileType = cave.getTileType(playerLocation);
        return tileType == Cave.WOOD;
    }

    public boolean isOnWumpus(Cave cave) {//ve se ta na madeira
        Location playerLocation = getLocation();
        int tileType = cave.getTileType(playerLocation);
        return tileType == Cave.WUMPUS;
    }
    public boolean isOnSpider(Cave cave) {//ve se ta na madeira
        Location playerLocation = getLocation();
        int tileType = cave.getTileType(playerLocation);
        return tileType == Cave.SPIDER;
    }

    public void adicionarItem(Item item) {
        if (mochila.size() < 3) {
            mochila.add(item);
            System.out.println("Item adicionado à mochila: " + item.getNome());
        } else {
            System.out.println("A mochila está cheia. Não é possível adicionar mais itens.");
        }
    }
    public void removeItem(Item item) {
        if (mochila.contains(item)) {
            mochila.remove(item);
            System.out.println("Item removido da mochila: " + item.getNome());
        } else {
            System.out.println("O item não está na mochila.");
        }
    }
    public String mostrarMochila() {
        if (mochila.isEmpty()) {
            return "";
        }
        String itens = "";
        for (Item item : mochila) {
            String itemFormatado = item.getNome();
            itens += itemFormatado + "\n";
        }
        return itens;
    }

    public void usarLanterna() {
        cargasLanters -= 1;

    }
    public int getcargas(){
        return cargasLanters;
    }




    public void criarFlecha() {
        // Verifique se há madeira na mochila
        if (madeiraColetada >= 1) {

            // Remova uma madeira da mochila
            removeItem(madeira);
            madeiraColetada--;

            // Crie uma flecha e adicione à lista de flechas
            Item flecha = new Item("Flecha");
            flechas.add(flecha);
            adicionarItem(flecha);


            System.out.println("Você criou uma flecha!");
        } else {
            System.out.println("Você não tem madeira suficiente para criar uma flecha.");
        }
    }

    public void removeFlecha(){
        for(Item item: mochila){
            if(item.getNome() == "Flecha"){
                removeItem(item);
                flechas.remove(0);
                break;
            }
        }

    }







    public int quantidadeFlechas(){
        return flechas.size();
    }
    public void draw(GraphicsContext gc){
        gc.drawImage(playerImage, loc.getCol()*50+Cave.xoffset, loc.getRow()*50+Cave.yoffset);
    }
}

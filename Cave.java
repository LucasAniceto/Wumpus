package com.aniceto;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class Cave {
    Random random = new Random();
    private int tiles[][];
    private int visible[][];
    private int fogMap[][];
    private int linha, coluna;

    private List<Location> pocos = new ArrayList<>();
    private List<Location> golds = new ArrayList<>();
    private List<Location> madeiras = new ArrayList<>();
    private Location monstro1;
    private Location monstro2;

    public static final int xoffset = 20;
    public static final int yoffset = 20;

    public static final int GROUND= 0, PIT=2, SPIDER=3, WUMPUS=4, GOLD=5, WOOD=6, POCOFECHADO=7,
            WIND=20, WEB=30, STINK=40, GLITER=50;

    private String path = "E:/Wpoo/WumpusPOO/src/main/java/com/aniceto";

    private Image groundImage, blackImage, glitterImage, goldImage, pitImage, halfImage, fullImage, deadImage, mochilaImage, pocofImage,
            spiderImage, stinkImage, webImage, wumpusImage, windImage, woodImage, arrowImage;


    public Cave() {
        tiles = new int[15][15];
        visible = new int[15][15];
        fogMap = new int[15][15];

        for(int row = 0; row < visible.length; row++) {
            Arrays.fill(visible[row], 1); // Inicialize com névoa (1) em todas as células
        }
        visible[14][0] = 0;

        linha = random.nextInt(14);  //set posição do ouro
        coluna = random.nextInt(14);



        //ouro
        while ((tiles[linha][coluna] != PIT && (linha != 14 || coluna != 0)) != true) {
            linha = random.nextInt(14);
            coluna = random.nextInt(14);
        }
        setTile(new Location(linha,coluna), GOLD);
        golds.add(new Location(linha, coluna));
        System.out.println("Posição do ouro adicionada: " + linha + ", " + coluna);

        //poços
        for(int i = 0; i < 5; i++) {
            linha = random.nextInt(14);
            coluna = random.nextInt(14);
            if (tiles[linha][coluna] == GROUND && ( linha != 14 || coluna != 0) ) {
                setTile(new Location(linha,coluna), PIT);
                pocos.add(new Location(linha, coluna));
                System.out.println("Posição do poço adicionada: " + linha + ", " + coluna);
            } else {
                i--;
            }
        }

        //madeiras
        for(int i = 0; i < 2; i++) {
            linha = random.nextInt(14);
            coluna = random.nextInt(14);
            if (tiles[linha][coluna] == GROUND  && (linha != 14 || coluna != 0) ) {
                tiles[linha][coluna] = WOOD;
                madeiras.add(new Location(linha, coluna));
                System.out.println("Posição da madeira adicionada: " + linha + ", " + coluna);
            } else {
                i--;
            }
        }

        //monstro 1
        linha = random.nextInt(14);
        coluna = random.nextInt(14);
        while ((tiles[linha][coluna] != PIT  && (linha != 14 || coluna != 0) ) != true) {
            linha = random.nextInt(14);
            coluna = random.nextInt(14);
        }
        setTile(new Location(linha,coluna), WUMPUS);
        monstro1 = new Location(linha, coluna);
        System.out.println("Posição do wumpus adicionada: " + linha + ", " + coluna);

        //monstro 2
        linha = random.nextInt(14);
        coluna = random.nextInt(14);
        while ((tiles[linha][coluna] != PIT  && (linha != 14 || coluna != 0)) != true) {
            linha = random.nextInt(14);
            coluna = random.nextInt(14);
        }
        setTile(new Location(linha,coluna), SPIDER);
        monstro2 = new Location(linha, coluna);
        System.out.println("Posição da aranha adicionada: " + linha + ", " + coluna);

        try {
            FileInputStream inputStream = new FileInputStream(path + "/imagens/groundTile.png");
            groundImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/blackTile.png");
            blackImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/glitterTile.png");
            glitterImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/goldTile.png");
            goldImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/pitTile.png");
            pitImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/spiderTile.png");
            spiderImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/stinkTile.png");
            stinkImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/webTile.png");
            webImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/windtile.png");
            windImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/wumpusTile.png");
            wumpusImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/woodTile.png");
            woodImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/arrow.png");
            arrowImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/dead.png");
            deadImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/half.png");
            halfImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/full.png");
            fullImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/mochilaTile.png");
            mochilaImage = new Image(inputStream);
            inputStream = new FileInputStream(path + "/imagens/pocoFTile.png");
            pocofImage = new Image(inputStream);
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }


    }

    public void drawn1(GraphicsContext gc) {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                if (visible[row][col] == 1) {
                    gc.drawImage(blackImage, xoffset + (col * 50), yoffset + (row * 50));
                }
            }}}

    public void drawn(GraphicsContext gc){
        for(int row=0; row < tiles.length;row++){
            for(int col=0; col < tiles[0].length; col++) {
                if(tiles[row][col] == GROUND){
                    gc.drawImage(groundImage, xoffset+(col*50), yoffset+(row*50));
                }
                else if(tiles[row][col] == SPIDER){
                    gc.drawImage(spiderImage, xoffset+(col*50), yoffset+(row*50));
                }
                else if(tiles[row][col] == GOLD){
                    gc.drawImage(goldImage, xoffset+(col*50), yoffset+(row*50));
                }
                else if(tiles[row][col] == WUMPUS){
                    gc.drawImage(wumpusImage, xoffset+(col*50), yoffset+(row*50));
                }
                else if(tiles[row][col] == PIT){
                    gc.drawImage(pitImage, xoffset+(col*50), yoffset+(row*50));
                }
                else if(tiles[row][col] == WEB){
                    gc.drawImage(webImage, xoffset+(col*50), yoffset+(row*50));
                }
                else if(tiles[row][col] == GLITER){
                    gc.drawImage(glitterImage, xoffset+(col*50), yoffset+(row*50));
                }
                else if(tiles[row][col] == STINK){
                    gc.drawImage(stinkImage, xoffset+(col*50), yoffset+(row*50));
                }
                else if(tiles[row][col] == WIND){
                    gc.drawImage(windImage, xoffset+(col*50), yoffset+(row*50));
                }
                else if(tiles[row][col] == WOOD){
                    gc.drawImage(woodImage, xoffset+(col*50), yoffset+(row*50));
                }
                else if(tiles[row][col] == POCOFECHADO){
                    gc.drawImage(pocofImage, xoffset+(col*50), yoffset+(row*50));
                }
            }
        }
    }//

    public int[][] getTiles() {
        return tiles;
    }






    public Image getGroundImage() {
        return groundImage;
    }
    public Image getWoodImage() {
        return woodImage;
    }

    public Image getPocofImage(){return pocofImage;}

    public Image getDeadImage(){return deadImage;}

    public Image getMochilaImage(){return mochilaImage;}

    public Image getFullImage(){return fullImage;}

    public Image getHalfImage() {
        return halfImage;
    }

    public Image getGoldImage() {
        return goldImage;
    }

    public Image getPitImage() {
        return pitImage;
    }

    public Image getSpiderImage() {
        return spiderImage;
    }

    public Image getWumpusImage() {
        return wumpusImage;
    }

    public void setTile(Location  loc, int tileID){
        //a localizacao eh valida?
        if(isValid(loc)){
            tiles[loc.getRow()][loc.getCol()] = tileID;
            updateTileHints(tileID, loc.getRow(), loc.getCol());
        }
    }
    public void setTileW(Location  loc, int tileID){
        //a localizacao eh valida?
        if(isValid(loc)){
            tiles[loc.getRow()][loc.getCol()] = tileID;
            updateTileHintsWood(tileID, loc.getRow(), loc.getCol());

        }
    }


    public void clearFog(Location loc) {
        if (isValid(loc)) {
            visible[loc.getRow()][loc.getCol()] = 0; // Define a névoa para 0 para indicar que não há névoa nesta célula
        }
    }

    public void clearAllFog() {
        for (int row = 0; row < fogMap.length; row++) {
            for (int col = 0; col < fogMap[0].length; col++) {
                if (visible[row][col] == 1) {
                    visible[row][col] = 2; // Define a névoa como vazia em todas as células que estavam com névoa ativa
                }
                else if (visible[row][col] == 2) {
                    visible[row][col] = 1; // Volta ao tabuleiro normal
                }
            }
        }
    }



    public void updateFog(Player player) {
        Location playerLocation = player.getLocation();
        int row = playerLocation.getRow();
        int col = playerLocation.getCol();

        // Limpe a névoa na célula do jogador e nas células vizinhas do jogador
        clearFog(new Location(row, col));          // Jogador
        clearFog(new Location(row - 1, col)); // Acima
        clearFog(new Location(row + 1, col)); // Abaixo
        clearFog(new Location(row, col - 1)); // Esquerda
        clearFog(new Location(row, col + 1)); // Direita

    }
    public List<Location> getPocos() {
        return pocos;
    }

    public List<Location> getGolds() {
        return golds;
    }

    public List<Location> getMadeiras() {
        return madeiras;
    }


    public void updateTileHints(int tileId, int row, int col){
        Location above = new Location(row-1, col);
        Location below = new Location(row+1, col);
        Location left = new Location(row, col-1);
        Location right = new Location(row, col+1);

        if(isValid(above) && tiles[row-1][col] == GROUND) {
            tiles[above.getRow()][above.getCol()] = tileId*10;
        }
        if(isValid(below)&& tiles[row+1][col] == GROUND) {
            tiles[below.getRow()][below.getCol()] = tileId*10;
        }
        if(isValid(left)&& tiles[row][col-1] == GROUND) {
            tiles[left.getRow()][left.getCol()] = tileId*10;
        }
        if(isValid(right)&& tiles[row][col+1] == GROUND) {
            tiles[right.getRow()][right.getCol()] = tileId*10;
        }
    }
    public void updateTileHintsWood(int tileId, int row, int col){
        Location above = new Location(row-1, col);
        Location below = new Location(row+1, col);
        Location left = new Location(row, col-1);
        Location right = new Location(row, col+1);

        if(isValid(above) && tiles[row-1][col] == GROUND) {
            tiles[above.getRow()][above.getCol()] = GROUND;
        }
        if(isValid(below)&& tiles[row+1][col] == GROUND) {
            tiles[below.getRow()][below.getCol()] = GROUND;
        }
        if(isValid(left)&& tiles[row][col-1] == GROUND) {
            tiles[left.getRow()][left.getCol()] = GROUND;
        }
        if(isValid(right)&& tiles[row][col+1] == GROUND) {
            tiles[right.getRow()][right.getCol()] = GROUND;
        }
    }

    public void updateTileHintsMonsters(int tileId, int row, int col){
        Location above = new Location(row-1, col);
        Location below = new Location(row+1, col);
        Location left = new Location(row, col-1);
        Location right = new Location(row, col+1);

        if(isValid(above) && (tiles[row-1][col] == STINK || tiles[row-1][col] == WEB)) {
            tiles[above.getRow()][above.getCol()] = GROUND;
        }
        if(isValid(below) && (tiles[row+1][col] == STINK || tiles[row+1][col] == WEB)) {
            tiles[below.getRow()][below.getCol()] = GROUND;
        }
        if(isValid(left)&& (tiles[row][col-1] == STINK || tiles[row][col-1] == WEB)) {
            tiles[left.getRow()][left.getCol()] = GROUND;
        }
        if(isValid(right)&& (tiles[row][col+1] == STINK || tiles[row][col+1] == WEB)) {
            tiles[right.getRow()][right.getCol()] = GROUND;
        }
    }
    // Dentro da classe Cave
    public int getTileType(Location loc) {
        if (isValid(loc)) {
            return tiles[loc.getRow()][loc.getCol()];
        }
        return -1; // Retorne um valor inválido se a localização não for válida
    }

    public void clearFogAtLocation(Location loc) {
        if (isValid(loc)) {
            visible[loc.getRow()][loc.getCol()] = 0; // Define a névoa para 0 para indicar que não há névoa nesta célula
        }
    }

    public Location getLocMonstro1(){
        return monstro1;
    }

    public Location getLocMonstro2(){
        return monstro2;
    }

    public void moverMonstro1(Location loc){
        linha = random.nextInt(4);
        if(tiles[loc.getRow()][loc.getCol()] == WUMPUS && linha == 0 && loc.getRow() < 14 && movimentacaoValida(new Location(loc.getRow()+1,loc.getCol()))) {
            setTile(new Location(loc.getRow(),loc.getCol()), GROUND);
            updateTileHintsMonsters(0,loc.getRow(),loc.getCol());
            setTile(new Location(loc.getRow()+1,loc.getCol()), WUMPUS);
            monstro1 = new Location(loc.getRow()+1,loc.getCol());
        }
        else if (tiles[loc.getRow()][loc.getCol()] == WUMPUS && linha == 1 && loc.getRow() > 0 && movimentacaoValida(new Location(loc.getRow()-1,loc.getCol()))) {
            setTile(new Location(loc.getRow(),loc.getCol()), GROUND);
            updateTileHintsMonsters(0,loc.getRow(),loc.getCol());
            setTile(new Location(loc.getRow()-1,loc.getCol()), WUMPUS);
            monstro1 = new Location(loc.getRow()-1,loc.getCol());
        }
        else if(tiles[loc.getRow()][loc.getCol()] == WUMPUS && linha == 2 && loc.getCol() < 14 && movimentacaoValida(new Location(loc.getRow(),loc.getCol()+1))){
            setTile(new Location(loc.getRow(),loc.getCol()), GROUND);
            updateTileHintsMonsters(0,loc.getRow(),loc.getCol());
            setTile(new Location(loc.getRow(),loc.getCol()+1), WUMPUS);
            monstro1 = new Location(loc.getRow(),loc.getCol()+1);
        }
        else if(tiles[loc.getRow()][loc.getCol()] == WUMPUS && linha == 3 && loc.getCol() > 0 && movimentacaoValida(new Location(loc.getRow(),loc.getCol()-1))){
            setTile(new Location(loc.getRow(),loc.getCol()), GROUND);
            updateTileHintsMonsters(0,loc.getRow(),loc.getCol());
            setTile(new Location(loc.getRow(),loc.getCol()-1), WUMPUS);
            monstro1 = new Location(loc.getRow(),loc.getCol()-1);
        }
    }

    public void moverMonstro2(Location loc){
        linha = random.nextInt(4);
        if (tiles[loc.getRow()][loc.getCol()] == SPIDER && linha == 0 && loc.getRow() < 14 && movimentacaoValida2(new Location(loc.getRow() + 1, loc.getCol()))) {
            setTile(new Location(loc.getRow(), loc.getCol()), GROUND);
            updateTileHintsMonsters(0, loc.getRow(), loc.getCol());
            setTile(new Location(loc.getRow() + 1, loc.getCol()), SPIDER);
            monstro2 = new Location(loc.getRow() + 1, loc.getCol());
        } else if (tiles[loc.getRow()][loc.getCol()] == SPIDER && linha == 1 && loc.getRow() > 0 && movimentacaoValida2(new Location(loc.getRow() - 1, loc.getCol()))) {
            setTile(new Location(loc.getRow(), loc.getCol()), GROUND);
            updateTileHintsMonsters(0, loc.getRow(), loc.getCol());
            setTile(new Location(loc.getRow() - 1, loc.getCol()), SPIDER);
            monstro2 = new Location(loc.getRow() - 1, loc.getCol());
        } else if (tiles[loc.getRow()][loc.getCol()] == SPIDER && linha == 2 && loc.getCol() < 14 && movimentacaoValida2(new Location(loc.getRow(), loc.getCol() + 1))) {
            setTile(new Location(loc.getRow(), loc.getCol()), GROUND);
            updateTileHintsMonsters(0, loc.getRow(), loc.getCol());
            setTile(new Location(loc.getRow(), loc.getCol() + 1), SPIDER);
            monstro2 = new Location(loc.getRow(), loc.getCol() + 1);
        } else if (tiles[loc.getRow()][loc.getCol()] == SPIDER && linha == 3 && loc.getCol() > 0 && movimentacaoValida2(new Location(loc.getRow(), loc.getCol() - 1))) {
            setTile(new Location(loc.getRow(), loc.getCol()), GROUND);
            updateTileHintsMonsters(0, loc.getRow(), loc.getCol());
            setTile(new Location(loc.getRow(), loc.getCol() - 1), SPIDER);
            monstro2 = new Location(loc.getRow(), loc.getCol() - 1);
        }

    }
    public boolean isValid(Location loc){
        return loc.getRow() >= 0 && loc.getRow() < tiles.length && loc.getCol() >= 0 && loc.getCol() < tiles[loc.getRow()].length;
    }

    public boolean movimentacaoValida (Location loc){
        if(tiles[loc.getRow()][loc.getCol()] == GROUND || tiles[loc.getRow()][loc.getCol()] == STINK) {
            return true;
        } return false;
    }

    public boolean movimentacaoValida2 (Location loc){
        if(tiles[loc.getRow()][loc.getCol()] == GROUND || tiles[loc.getRow()][loc.getCol()] == WEB) {
            return true;
        } return false;
    }

}//acaba classe

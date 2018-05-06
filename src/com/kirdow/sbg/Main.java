package com.kirdow.sbg;

public class Main {

    public static void main(String[] args) {
        GameSettings.init(args);

        BlockGame game = new BlockGame();
        game.start();

        game.join();

        System.out.println("Game ended!");
    }

}

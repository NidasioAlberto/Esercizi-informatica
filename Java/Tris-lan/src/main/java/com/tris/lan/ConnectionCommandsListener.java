package com.tris.lan;

interface ConnectionCommandsListener {
    void onHostGame();

    void onConnectToServer(String ip);

    void onStopGame();
}
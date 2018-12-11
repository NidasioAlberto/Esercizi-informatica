package com.tris.lan;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.Image;
import java.io.BufferedReader;
import org.apache.commons.io.FileUtils;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class UiManager {
    static final int PORT = 2000;

    static final int width = 600;
    static final int height = 665;

    Image xIcon;
    Image oIcon;

    int xPoints = 0;
    int oPoints = 0;

    JFrame frame;
    MainPanel mainPanel;
    ControlPanel controlPanel;

    int thisTurnIcon = 1;
    int tableStatus[][] = new int[3][3];

    ServerSocket server;
    Socket connection;
    boolean connected = false;
    PrintWriter connectionOut;
    int playerType = -1;

    public UiManager() {
        try {
            xIcon = ImageIO.read(getClass().getResource("resources/X.png"));
            oIcon = ImageIO.read(getClass().getResource("resources/O.png"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void showInterface() {
        //create the JFrame that will hold avery graphical object
		frame = new JFrame();
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(600);
        splitPane.setEnabled(false);
        
        //create the 9 futton for the 9 spots on the grid
        mainPanel = new MainPanel();

        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
               System.out.println("hey");

               //check the spot of the click

               if(thisTurnIcon == playerType && connected) {
                    int x = e.getX() / (width / 3);
                    int y = e.getY() / (width / 3);

                    if(connection.isConnected()) {
                        connectionOut.print(x + "," + y);
                        if(tableStatus[y][x] == 0) {
                            tableStatus[y][x] = thisTurnIcon;
                            
                            System.out.println(tableStatus[0][0] + " " + tableStatus[0][1] + " " + tableStatus[0][2]);
                            System.out.println(tableStatus[1][0] + " " + tableStatus[1][1] + " " + tableStatus[1][2]);
                            System.out.println(tableStatus[2][0] + " " + tableStatus[2][1] + " " + tableStatus[2][2]);
                
                            if(thisTurnIcon == 1) {
                                    mainPanel.addImage(xIcon, x * (width / 3), y * (width / 3), width / 3, width / 3);
                                    thisTurnIcon = 2;
                                } else {
                                    mainPanel.addImage(oIcon, x * (width / 3), y * (width / 3), width / 3, width / 3);
                                    thisTurnIcon = 1;
                                }
    
                                //check the winner
                                int winner = checkWinner(tableStatus);
    
                                if(winner > 0 || checkFullTable(tableStatus)) {
                                    System.out.println("winner : " + winner);
    
                                    //update the counters
                                    if(winner == 1) xPoints++;
                                    else oPoints++;
    
                                    //show a dialog with the points
                                    EndGameDialog endGameDialog = new EndGameDialog(frame, xPoints, oPoints,
                                        new ImageIcon(xIcon.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH)),
                                        new ImageIcon(oIcon.getScaledInstance(100, 100,  java.awt.Image.SCALE_SMOOTH)));
    
                                    endGameDialog.onContinue = new Runnable(){
                                        public void run() {
                                            System.out.println("continue");
                                        }
                                    };
                                    
                                    endGameDialog.onEndGame = new Runnable(){
                                        public void run() {
                                            System.out.println("end game");
                                            frame.setVisible(false);
                                            System.exit(0);
                                        }
                                    };
    
                                    endGameDialog.onSaveAndClose = new Runnable(){
                                        public void run() {
                                            System.out.println("save and close");
                                            frame.setVisible(false);
    
                                            //save the core on file
                                            JFileChooser fileChooser = new JFileChooser();
                                            try {
                                                if(fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) FileUtils.write(fileChooser.getSelectedFile(), "{\"winner\":" + (xPoints > oPoints ? "\"X\"" : "\"O\"") + ", \"points\": {\"X\":\"" + xPoints + "\",\"X\":\"" +oPoints + "\"}}");
                                            } catch(IOException e) {
                                                e.printStackTrace();
                                            }
    
                                            System.exit(0);
                                        }
                                    };
    
                                    endGameDialog.showEndGameDialog();
    
                                    //clear the table and the jPanel
                                    for(int i = 0; i < 3; i++) for(int j = 0; j < 3; j++) tableStatus[i][j] = 0;
    
                                    (new Thread(new Runnable(){
                                    
                                        public void run() {
                                            try {
                                                Thread.sleep(1000);
    
                                                mainPanel.clearScreen();
                                            } catch(InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    })).run();
                                } 
    
                        } else {
                            //there's already a sign in the spot
                        }
                    }
                    
                }
            }
        });

        splitPane.setTopComponent(mainPanel);

        controlPanel = new ControlPanel(new ConnectionCommandsListener(){
            public void onStopGame() {
                try {
                    connection.close();
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                controlPanel.removeAll();
                controlPanel.setLayout(controlPanel.createPaneLayout(ControlPanel.BEFORE_GAME_MODE));
            }
        
            public void onHostGame() {
                controlPanel.removeAll();
                controlPanel.setLayout(controlPanel.createPaneLayout(ControlPanel.SERVER_MODE));

                (new Thread(new Runnable() {
                    public void run() {
                        try {
                            server = new ServerSocket(PORT);

                            connection = server.accept();

                            connectionOut = new PrintWriter(connection.getOutputStream());

                            connected = true;

                            playerType = 1;
                            thisTurnIcon = 1;
    
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                            while(connection.isConnected()) {
                                System.out.println(bufferedReader.readLine());
                            }
                        } catch(IOException e) {
                            e.printStackTrace();
                        }
                    }
                })).start();
            }
        
            public void onConnectToServer(final String ip) {
                System.out.println(ip);

                (new Thread(new Runnable(){
                    public void run() {
                        try {
                            connection = new Socket(ip, PORT);
                            connectionOut = new PrintWriter(connection.getOutputStream());
                            connected = true;
                            playerType = 2;
                            thisTurnIcon = 1;
                            System.out.println("connected");

                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                            while(connection.isConnected()) {
                                System.out.println(bufferedReader.readLine());
                            }
                        } catch (UnknownHostException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                })).start();
            }
        });

        splitPane.setBottomComponent(controlPanel);

        frame.add(splitPane);

        frame.setVisible(true);
    }
    
    private int checkWinner(int tableStatus[][]) {
        //horizontal
        for(int i = 0; i < 3; i++) {
            if(tableStatus[i][0] == tableStatus[i][1] && tableStatus[i][1] == tableStatus[i][2] && tableStatus[i][0] != 0) {
                return(tableStatus[i][0]);
            }
        }

        //vertical
        for(int i = 0; i < 3; i++) {
            if(tableStatus[0][i] == tableStatus[1][i] && tableStatus[1][i] == tableStatus[2][i] && tableStatus[0][i] != 0) {
                return(tableStatus[0][i]);
            }
        }

        //diagonal
        if(tableStatus[0][0] == tableStatus[1][1] && tableStatus[1][1] == tableStatus[2][2] && tableStatus[0][0] != 0) {
            return(tableStatus[0][0]);
        }
        if(tableStatus[0][2] == tableStatus[1][1] && tableStatus[1][1] == tableStatus[2][0] && tableStatus[0][2] != 0) {
            return(tableStatus[0][2]);
        }

        return(-1);
    }

    private boolean checkFullTable(int tableStatus[][]) {
        for(int i = 0; i < 3; i++) for(int j = 0; j < 3; j++) if(tableStatus[i][j] == 0) return(false);
        return(true);
    }
}
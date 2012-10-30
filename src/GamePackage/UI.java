package GamePackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UI {
       
    JFrame f;
    
    JMenuItem creat = new JMenuItem("Start new Game");
    Dimension a = new Dimension(800, 800);
    Font displayFont = new Font("Serif", Font.BOLD, 18);
    
    
    
    public UI() {
       // BuddyInfo
    }
        
    private JPanel createContentPane() {
        JPanel gui = new JPanel();
        gui.setBackground(Color.black);
        JPanel gamePanel = new GamePanel();
        gui.add(gamePanel);
        return gui;
    }
            
    private JMenuBar creatMenuBar() {
        JMenuBar menu = new JMenuBar();
        JMenu zuuM = new JMenu("ZuuM");
        creat.addActionListener(new actionPerformance());
        zuuM.add(creat);
        menu.add(zuuM);
        
        return menu;
    }
    public void creatNewGUI() {
        f = new JFrame("ZuuM");
        f.setSize(a);
        
        f.setContentPane(this.createContentPane());
        f.setJMenuBar(this.creatMenuBar());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private class actionPerformance implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == creat) {
                //game.play();
                System.out.println("Game start");
            }     
        }
    }
    
    public static void main(String args[]) {    
        UI game = new UI();
        game.creatNewGUI();
    }

}

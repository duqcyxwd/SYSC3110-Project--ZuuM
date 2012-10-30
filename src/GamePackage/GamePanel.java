package GamePackage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class GamePanel extends JPanel implements ActionListener {
    

    public GamePanel() {
        super();
        setLayout(new GridLayout(4,4));
        for (int i=0; i < 12; i++) {
            JButton room = new JButton("Zoom");
            room.setSize(20, 40);
            add(room);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
}
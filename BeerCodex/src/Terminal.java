import graphicsLib.UbuntuFont;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class Terminal {
    GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private int fontSize;
    private static JPanel terminalPanel,cliPanel;
    JTextArea history = historyField();
    public Terminal(int sizing){
        fontSize = sizing;
        terminalPanel = new JPanel();
        terminalPanel.setOpaque(false);
        terminalPanel.setLayout(new BorderLayout());
        cliPanel = new JPanel();
        cliPanel.setOpaque(false);
        cliPanel.add(ubuntuTF(), BorderLayout.PAGE_END);
        JPanel cliHistoryPanel = new JPanel();
        cliHistoryPanel.setOpaque(false);
        terminalPanel.add(cliHistoryPanel, BorderLayout.CENTER);
        cliHistoryPanel.add(history);
        terminalPanel.add(cliPanel);
    }
    public JPanel getPanel(){
        return terminalPanel;
    }
    private JFormattedTextField ubuntuTF(){
        JFormattedTextField tf = new JFormattedTextField("> app cli here");
        tf.setFont(UbuntuFont.MonoR.getFont((float) fontSize));
        tf.setOpaque(false);
        tf.setForeground(Color.LIGHT_GRAY);
        tf.setBorder(BorderFactory.createEmptyBorder());
        tf.addKeyListener(new KeyListener(){
            public void keyTyped(KeyEvent e) {

            }
            public void keyPressed(KeyEvent e) {

            }
            public void keyReleased(KeyEvent e) {

            }
        });
        return tf;
    }
    private JTextArea historyField(){
        JTextArea hist = new JTextArea();
        hist.setFont(UbuntuFont.MonoR.getFont((float)fontSize));
        hist.setOpaque(false);
        hist.setForeground(Color.LIGHT_GRAY);
        hist.setEditable(false);
        return hist;
    }
    private void refresh(){
        SwingUtilities.updateComponentTreeUI(terminalPanel);
    }
}

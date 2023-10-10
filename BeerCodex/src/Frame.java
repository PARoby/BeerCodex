import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

enum pages implements page{
    RECIPE_LIST(new RecipesListPage(Toolkit.getDefaultToolkit().getScreenSize().height)),
    SPECIFIC_RECIPE(new RecipePage(Toolkit.getDefaultToolkit().getScreenSize().height));
    page pg;
    pages(page obj){
        pg = obj;
    }
    public JPanel page(){
        return pg.page();
    }
    @Override
    public void update(String specific) {
        pg.update(specific);
    }

}
public class Frame implements ActionListener  {
    public static String appName = "Codex des bières - version 0.1";
    public static String activeRecipe = "";
    private static JFrame mainFrame;
    public static ImageIcon appIcon = new ImageIcon("logo.png");
    public static double logoImageRatio = (double)appIcon.getIconHeight()/(double)appIcon.getIconWidth();
    Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    private static JPanel mainPanel, workPanel;
    private static JButton logoLabel;
    public static pages activePage;
    private GridBagConstraints c;
    private JMenuBar mbar;
    private JMenu action;
    Frame(){
        mainFrame = new JFrame(appName);
        mainFrame.setIconImage(appIcon.getImage());
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setSize(1200,900);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.DARK_GRAY);
        workPanel = new JPanel(new GridBagLayout());
        workPanel.setOpaque(false);

        setMenuBar();
        mainFrame.add(mbar);

        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTHWEST;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.5;
        c.weightx = 0.5;


        ImageIcon inAppLogo = new ImageIcon(appIcon.getImage().getScaledInstance((int)Math.round(size.getWidth()/24*logoImageRatio),
                (int)Math.round(size.getWidth()/24),Image.SCALE_DEFAULT));
        logoLabel = new JButton(inAppLogo);
        logoLabel.addActionListener(this);

        mainPanel.add(logoLabel,BorderLayout.PAGE_START);

        mainFrame.add(mainPanel);
        terminal(true);

        changePageTo(pages.RECIPE_LIST);
        mainPanel.add(workPanel, BorderLayout.CENTER);

    }


    public void show(){

        mainFrame.setVisible(true);
    }
    public void changePageTo(pages pageToActivate){
        pageToActivate.update(activeRecipe);
        workPanel.removeAll();
        workPanel.add(pageToActivate.page(), c);
        SwingUtilities.updateComponentTreeUI(workPanel);
        activePage = pageToActivate;
    }
    private void setMenuBar(){
        mbar = new JMenuBar();
        action = new JMenu("Changer de page");
        mbar.add(action);
        workPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_1, 0), KeyEvent.VK_1);
        workPanel.getActionMap().put(KeyEvent.VK_1, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (activePage == pages.RECIPE_LIST) changePageTo(pages.SPECIFIC_RECIPE);
                else changePageTo(pages.RECIPE_LIST);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == logoLabel) {
            String[] newLine =  {"new name","new def","new style","new note"};
            try {
                dbWiz.save(newLine, "recipes", tableSyntax.DB_MAIN_COL_NAMES);
                changePageTo(activePage);
                activePage.update(activeRecipe);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    private void terminal(boolean visible) {
        JWindow terminalWindow = new JWindow(mainFrame);
        terminalWindow.setLayout(new BorderLayout());
        terminalWindow.getContentPane().setBackground(new Color(0,0,0,0));
        int terminalDim = (int) size.getWidth()/10;
        terminalWindow.setSize(new Dimension(terminalDim, terminalDim));
        terminalWindow.setBackground(new Color(0,0,0,150));
        Terminal terminalInstance = new Terminal((int) (terminalDim/10));
        terminalWindow.setOpacity(0.0f);

        terminalWindow.add(terminalInstance.getPanel());
        terminalWindow.setVisible(visible);
    }
}


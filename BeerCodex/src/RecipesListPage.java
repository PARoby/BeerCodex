import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

import graphicsLib.UbuntuFont;

public class RecipesListPage implements page{
    private static JPanel recipesPanel;
    private float writingSize;
    private Font font = UbuntuFont.R.getFont();
    private Insets stdInset = new Insets(2,2,2,2);
    private Insets tableThirdInset;
    private String[] titles = {"Nom", "Définition", "Style", "Note", "Date"};
    private static String[][] info = {{"Indéfini", "Indéfini", "Indéfini", "Indéfini", "Indéfini"}};
    RecipesListPage(int sizing) {

        try {
            info = dbWiz.load(tableSyntax.DB_MAIN_COL_NAMES, "recipes", -1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        writingSize = Math.round((float)sizing/60f);
        writingSize = writingSize > 10 ? writingSize : 10f;
        font = font.deriveFont(writingSize);

        tableThirdInset = new Insets(2, 2, (int) (writingSize * 0.6), 2);
        makeListPanel();
    }
    private void makeListPanel(){
        GridBagLayout layout = new GridBagLayout();
        JPanel panel = new JPanel(layout);
        panel.setOpaque(false);

        int i = 0;
        for (String title : titles) {
            panel.add(ubuntuLabel(title), constraint(i, 0));
            i++;
        }

        for (int index = 1; index < info.length+1; index++) {
            for (int col = 0; col < titles.length; col++) {
                panel.add(ubuntuLabel(info[index-1][col]), constraint(col, index));
                i++;
            }
        }
        recipesPanel = panel;
    }

    private GridBagConstraints constraint(int xCoord, int yCoord){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = xCoord;
        c.gridy = yCoord;
        c.gridheight = 1;
        c.gridwidth = 1;
        c.weightx = 0.5;
        c.insets = yCoord == 0 || (yCoord % 3) != 0 ? stdInset : tableThirdInset;
        return c;
    }
    private JLabel ubuntuLabel(String content) {
        JLabel label = new JLabel(content);
        label.setFont(font);
        label.setForeground(Color.LIGHT_GRAY);
        return label;
    }
    public JPanel page() {
        return recipesPanel;
    }
    public void update(String specific){
        try {
            info = dbWiz.load(tableSyntax.DB_MAIN_COL_NAMES, "recipes", -1);
            makeListPanel();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

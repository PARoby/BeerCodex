import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import graphicsLib.UbuntuFont;


public class RecipePage implements page {
    private boolean modif = true;
    private JPanel recipePanel, idPanel, grainPanel, additivesPanel;
    private float writingSize;
    private Font font = UbuntuFont.R.getFont();
    private Insets stdInset = new Insets(2,2,2,2);
    private Insets tableThirdInset;
    private String[] grainTitles = {"Type de grain", "Recette de base", "Cette recette"};
    private String[] additivesTitles = {"Type", "Quantité de base", "temps", "Quantité pour cette recette", "temps"};
    private String name = "Bière 1",style = "Style",overallSize = "Taille de la recette", waterSrc = "Source", waterQty = "Quantité d\'eau";
    private String[] grain = {"type1", "type2"};
    private double[][] grainQty = {{1d,2d},{3d,4d}};
    private double Temp = 5d;
    private String Comments = "Commentaire";
    private double rinseWaterQty = 6d, boilTime = 7d;
    private String[] additive = {"Houblon 1", "Houblon 2"};
    private double[][] additivesQty = {{8d,9d,10d,11d}, {14d,15d,16d,17d}};
    private String yeast = "levure 1";
    private double og = 12d;
    private double fg = 13d;
    private Border roundedBorder = new LineBorder(Color.LIGHT_GRAY,2,true);
    private JFormattedTextField[] textFields = new JFormattedTextField[12], variousNumFields = new JFormattedTextField[7],
        grainNumFields = new JFormattedTextField[20], grainTextFields = new JFormattedTextField[10],
        additivesNumFields = new JFormattedTextField[20], additivesTextFields = new JFormattedTextField[5];
    private JComboBox[] grains = new JComboBox[6];
    private JComboBox[] additives = new JComboBox[6];

    RecipePage(int sizing){
        writingSize = Math.round(sizing/60);
        writingSize = writingSize > 10 ? writingSize : 10f;
        font = font.deriveFont(writingSize);

        tableThirdInset = new Insets(2,2,(int)(writingSize*0.6),2);
        roundedBorder = new LineBorder(Color.LIGHT_GRAY,(int) Math.round(sizing/700),true);

        GridBagLayout layout = new GridBagLayout();
        recipePanel = new JPanel(layout);
        recipePanel.setOpaque(false);
        recipePanel.setBorder(roundedBorder);
        int currentLine = 0;

        idPanel = new JPanel(new GridBagLayout());
        idPanel.setOpaque(false);
        idPanel.setBorder(roundedBorder);


        // Line 1 ------------------------------------------
        idPanel.add(ubuntuLabel("Nom :"), constraint(0,currentLine,2));
        textFields[0] = ubuntuTextField(name);
        idPanel.add(textFields[0], constraint(2,currentLine,4));
        idPanel.add(ubuntuLabel("Style :"), constraint(6,currentLine,2));
        textFields[1] = ubuntuTextField(style);
        idPanel.add(textFields[1], constraint(8,currentLine,4));
        currentLine++;

        // Line 2 ------------------------------------------
        idPanel.add(ubuntuLabel("Taille de la recette :"), constraint(0,currentLine,2));
        variousNumFields[0] = ubuntuTextField(overallSize);
        idPanel.add(variousNumFields[0], constraint(2,currentLine,4));
        idPanel.add(ubuntuLabel("source de l\'eau : "), constraint(6,currentLine,2));
        textFields[2] = ubuntuTextField(waterSrc);
        idPanel.add(textFields[2], constraint(8,currentLine,4));
        currentLine++;

        // Line 3 ------------------------------------------
        idPanel.add(new JLabel(),constraint(0,currentLine,6));
        idPanel.add(ubuntuLabel("Quantité d\'eau : "), constraint(6,currentLine,2));
        variousNumFields[1] = ubuntuTextField(waterQty);
        idPanel.add(variousNumFields[1], constraint(8,currentLine,4));
        currentLine = 0;
        recipePanel.add(idPanel, constraint(0,currentLine,12));
        currentLine++;
        // Line 4
        newParagraphSpacing(currentLine);
        currentLine++;

        grainPanel = new JPanel(new GridBagLayout());
        grainPanel.setOpaque(false);
        grainPanel.setBorder(roundedBorder);

        // -----------------------------------------------------------------------------
        // ------------------------------ Table of grains ------------------------------
        // -----------------------------------------------------------------------------
        int grainTableLine = 0;
        for (int index = 0; index < grainTitles.length; index++) {
            grainPanel.add(ubuntuLabel(grainTitles[index]), constraint(index*4, grainTableLine,4));
        }
        grainTableLine++;
        for (int index = 0; index < grain.length; index++) {
            grainTextFields[3+index] = ubuntuTextField(grain[index]);
            grainPanel.add(grainTextFields[3+index], constraint(0, grainTableLine,4));
            grainNumFields[index*2] = ubuntuTextField(Double.toString(grainQty[index][0]));
            grainPanel.add(grainNumFields[index*2], constraint(4, grainTableLine,4));
            grainNumFields[1+index*2] = ubuntuTextField(Double.toString(grainQty[index][1]));
            grainPanel.add(grainNumFields[1+index*2], constraint(8, grainTableLine,4));
            grainTableLine++;
        }

        recipePanel.add(grainPanel, constraint(0,currentLine,12));
        currentLine++;

        newParagraphSpacing(currentLine);
        currentLine++;

        // ---------------------------- Lines between tables ----------------------------
        recipePanel.add(ubuntuLabel("Temp. : "), constraint(0,currentLine,4));
        recipePanel.add(ubuntuLabel(Double.toString(Temp)), constraint(4,currentLine,8));
        currentLine++;
        recipePanel.add(ubuntuLabel("Commentaire : "), constraint(0,currentLine,4));
        recipePanel.add(ubuntuLabel(Comments), constraint(4,currentLine,8));
        currentLine++;
        recipePanel.add(ubuntuLabel("Quantité d\'eau de rinçage : "), constraint(0,currentLine,4));
        recipePanel.add(ubuntuLabel(Double.toString(rinseWaterQty)), constraint(4,currentLine,8));
        currentLine++;
        recipePanel.add(ubuntuLabel("Temps d\'ébulition: "), constraint(0,currentLine,4));
        recipePanel.add(ubuntuLabel(Double.toString(boilTime)), constraint(4,currentLine,8));
        currentLine++;

        newParagraphSpacing(currentLine);
        currentLine++;

        // -----------------------------------------------------------------------------
        // ---------------------------- Table of additives  ----------------------------
        // -----------------------------------------------------------------------------
        int additivesTableLine = 0;
        additivesPanel = new JPanel(new GridBagLayout());
        additivesPanel.setOpaque(false);
        additivesPanel.setBorder(roundedBorder);
        for (int index = 0; index < additivesTitles.length; index++) {
            additivesPanel.add(ubuntuLabel(additivesTitles[index]), constraint(index*2+2, additivesTableLine,index == 0 ? 4:2));
        }

        additivesTableLine++;

        for (int index = 0; index < additive.length; index++) {
            additivesTextFields[index] = ubuntuTextField(additive[index]);
            additivesPanel.add(additivesTextFields[index], constraint(0, additivesTableLine,4));
            for (int col = 0; col < additivesQty[0].length; col++){
                additivesNumFields[index*4+col] = ubuntuTextField(Double.toString(additivesQty[index][col]));
                additivesPanel.add(additivesNumFields[index*4+col], constraint(col*2+4,
                        additivesTableLine, index == 0 ? 4:2));
            }
            additivesTableLine++;
        }
        recipePanel.add(additivesPanel, constraint(0,currentLine,12));
        currentLine++;

        newParagraphSpacing(currentLine);
        currentLine++;

        // ---------------------------- Lines after tables ----------------------------
        recipePanel.add(ubuntuLabel("Type de levure : "), constraint(0,currentLine,4));
        recipePanel.add(ubuntuLabel(yeast), constraint(4,currentLine,8));
        currentLine++;
        recipePanel.add(ubuntuLabel("OG : "), constraint(0,currentLine,3));
        recipePanel.add(ubuntuLabel(Double.toString(og)), constraint(3,currentLine,3));
        recipePanel.add(ubuntuLabel("FG: "), constraint(6,currentLine,3));
        recipePanel.add(ubuntuLabel(Double.toString(fg)), constraint(9,currentLine,3));

    }

    private GridBagConstraints constraint(int xCoord, int yCoord){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = xCoord;
        c.gridy = yCoord;
        c.gridheight = 1;
        c.gridwidth = 1;
        // c.weightx = 0.5;
        c.insets = stdInset;
        return c;
    }
    private GridBagConstraints constraint(int xCoord, int yCoord, int width){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = xCoord;
        c.gridy = yCoord;
        c.gridheight = 1;
        c.gridwidth = width;
        c.weightx = 0.5;
        c.insets = stdInset;
        return c;
    }
    private GridBagConstraints constraint(int xCoord, int yCoord, int width, boolean tableThird){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = xCoord;
        c.gridy = yCoord;
        c.gridheight = 1;
        c.gridwidth = width;
        c.weightx = 0.5;
        c.insets = tableThird ? stdInset : tableThirdInset;
        return c;
    }
    private GridBagConstraints nextParagraphConstraints(int yCoord, int spacing){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = yCoord;
        c.gridheight = 1;
        c.gridwidth = 12;
        c.insets = new Insets(0,0,spacing,0);
        return c;
    }
    private void newParagraphSpacing(int currentLine){
        recipePanel.add(new JLabel(),nextParagraphConstraints(currentLine, (int)writingSize));
    }
    private JComponent ubuntuLabel(String content) {
        JLabel label = new JLabel(content);
        label.setFont(font);
        label.setForeground(Color.LIGHT_GRAY);
        return label;
    }
    private JFormattedTextField ubuntuTextField(String content){
        JFormattedTextField label = new JFormattedTextField();
        label.setText(content);
        label.setFont(font);
        label.setOpaque(false);
        label.setCaretColor(Color.LIGHT_GRAY);
        label.setForeground(Color.LIGHT_GRAY);
        if (!modif) label.setEditable(false);
        label.setBorder(BorderFactory.createEmptyBorder());
        return label;
    }
    public JPanel page() {
        return recipePanel;
    }

    public void update(String specific) {

    }
}

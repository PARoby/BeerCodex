package graphicsLib;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;
import java.io.*;
import javax.swing.JLabel;

public enum UbuntuFont {
     B("Ubuntu-B"), BI("Ubuntu-BI"), C("Ubuntu-C"), L("Ubuntu-L"),
    LI("Ubuntu-LI"), M("Ubuntu-M"), MI("Ubuntu-MI"), R("Ubuntu-R"),
    RI("Ubuntu-RI"), Th("Ubuntu-Th"), MonoB("UbuntuMono-B"),
    MonoBI("UbuntuMono-BI"), MonoR("UbuntuMono-R"), MonoRI("UbuntuMono-RI");
    private Font font;
    UbuntuFont(String fName){
        try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            String path = "ubuntu-font/" + fName + ".ttf";
            InputStream is = new FileInputStream(path);
            font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(12f);
            ge.registerFont(font);
        } catch(FileNotFoundException e) {
            System.out.println("Ubuntu font file series is not in the same folder as graphicsLib package. " +
                    "Download: https://design.ubuntu.com/font");
            e.printStackTrace();
        } catch(FontFormatException | IOException e){
            System.out.println("Could not create the font");
            e.printStackTrace();
        }
    }
    public Font getFont(){return font;}
    public Font getFont(float size) {return font.deriveFont(size);}
}


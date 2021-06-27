package GUI.Template;

import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.OceanTheme;

public class WhiteTheme extends OceanTheme{
    public  ColorUIResource getControlShadow(){
        return new ColorUIResource(255,255,255);
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translator;

import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author Julio
 */
public class MyCellRenderer extends DefaultListCellRenderer {
   public static final String HTML_1 = "<html><body style='width: ";
   public static final String HTML_2 = "px'>";
   public static final String HTML_3 = "</html>";
   private int width;

   public MyCellRenderer(int width) {
      this.width = width;
   }

   @Override
   public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
      String text = HTML_1 + String.valueOf(width) + HTML_2 + value.toString() + HTML_3;
      Component c = super.getListCellRendererComponent(list, text, index, isSelected,cellHasFocus);
      if(isSelected)c.setForeground(Color.BLACK);
      return c;
   }
}

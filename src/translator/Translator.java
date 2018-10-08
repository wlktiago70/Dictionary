/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package translator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;

/**
 *
 * @author Samsung
 */
public class Translator {

    /**
     * @param args the command line arguments
     */
 
    /*public static JFrame Janela = new JFrame("翻譯抱佛腳");
    public static JTabbedPane Fundo = new JTabbedPane();
    public static JMenuBar Menu = new JMenuBar();
    public static JMenu Arquivo = new JMenu("檔案"),
                        Exibicao = new JMenu("檢視"),
                        Linguagens = new JMenu("語言");                        
    public static JMenuItem Sair = new JMenuItem("離開"),                            
                            Chinese = new JMenuItem("中文"),
                            Portuguese = new JMenuItem("葡文");*/
    public static JFrame Janela;
    public static JTabbedPane Fundo;
    public static JMenuBar Menu;
    public static JMenu Arquivo,
                        Exibicao,
                        Linguagens;                        
    public static JMenuItem Sair,                            
                            Chinese,
                            Portuguese;
    public static void main(String[] args) {
        // TODO code application logic here
        setNimbusLookAndFeel();
        
        Janela = new JFrame("翻譯抱佛腳");
        Fundo = new JTabbedPane();
        Menu = new JMenuBar();
        Arquivo = new JMenu("檔案");
        Exibicao = new JMenu("檢視");
        Linguagens = new JMenu("語言");                        
        Sair = new JMenuItem("離開");                            
        Chinese = new JMenuItem("中文");
        Portuguese = new JMenuItem("葡文");
        
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int centrox = d.width / 2, centroy = d.height / 2, tamx = 900, tamy = 600;
        Janela.setBounds(centrox - tamx / 2, centroy - tamy / 2, tamx, tamy);
        Janela.setLayout(new BorderLayout());
        Fundo.addTab("翻譯查詢", new Dictionary());
        //Fundo.addTab("增加翻譯", new Include());
        //Fundo.addTab("直方圖", new Histogram());
        Janela.add(Fundo,BorderLayout.CENTER);
        //Linguagens.add(Chinese);
        //Linguagens.add(Portuguese);
        Arquivo.add(Sair);
        //Exibicao.add(Linguagens);
        Menu.add(Arquivo);
        //Menu.add(Exibicao);
        Janela.setJMenuBar(Menu);
        Janela.setVisible(true);
        Janela.setResizable(false);
        Janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Sair.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
    }
    private static void setNimbusLookAndFeel() {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}

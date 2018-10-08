/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package translator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Samsung
 */
public class Dictionary extends JPanel{
    public static JPanel PainelProcura = new JPanel(new FlowLayout());
    public static JTextField Campo = new JTextField(15);
    public static JButton AND = new JButton("與");
    public static JButton OR = new JButton("或");
    public static JButton NOT = new JButton("非");
    public static JButton OK = new JButton("OK");
    public static JRadioButton chpt = new JRadioButton("中翻葡",true),
                               ptch = new JRadioButton("葡翻中",false);
    public static ButtonGroup grupo = new ButtonGroup();
    public static JTextArea Resultado = new JTextArea();
    public static JScrollPane FundoResultado = new JScrollPane(Resultado);
    private Database dados = new Database();
    public static JComboBox ListaDiretorios;
    public static JComboBox ListaArquivos;
    private String listaDir[];
    private String listaArq[];
    public static ResultPanel ExibicaoResultados = new ResultPanel();
    public static Language FirstLang = Language.CHINESE;
    public static Language SecondLang = Language.PORTUGUESE;
    public static JPanel RadioPanel = new JPanel();
    public static JPanel OperadoresLogicos = new JPanel();
    public Dictionary()
    {
        this.setLayout(new BorderLayout());
        RadioPanel.setLayout(new GridLayout(2,1,5,0));
        RadioPanel.add(chpt);
        RadioPanel.add(ptch);
        grupo.add(chpt);
        grupo.add(ptch);
        PainelProcura.add(RadioPanel);
        PainelProcura.add(new JLabel("   我要在"));
        listaDir = new String[dados.getFolderList().size()];
        ListaDiretorios = new JComboBox(dados.getFolderList().toArray(listaDir));
        PainelProcura.add(ListaDiretorios);
        PainelProcura.add(new JLabel("裡的"));
        listaArq = new String[dados.listOfFiles((String)ListaDiretorios.getSelectedItem()).size()];
        ListaArquivos = new JComboBox(dados.listOfFiles((String)ListaDiretorios.getSelectedItem()).toArray(listaArq));
        PainelProcura.add(ListaArquivos);
        
        ListaDiretorios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                List<String> aux = dados.listOfFiles((String)ListaDiretorios.getSelectedItem());
                ListaArquivos.removeAllItems();
                for(String str: aux)ListaArquivos.addItem(str);             
            }
        });
        /*PainelProcura.add(EscolhaDeArquivo);*/
        PainelProcura.add(new JLabel("查詢"));
        OperadoresLogicos.setLayout(new GridLayout(1,3,-5,5));
        OperadoresLogicos.add(AND);
        OperadoresLogicos.add(OR);
        OperadoresLogicos.add(NOT);
        PainelProcura.add(Campo);        
        PainelProcura.add(OperadoresLogicos);        
//        PainelProcura.add(AND);
//        PainelProcura.add(OR);
//        PainelProcura.add(NOT);
        PainelProcura.add(OK);
        Resultado.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));
        this.add(PainelProcura,BorderLayout.NORTH);
        //this.add(FundoResultado,BorderLayout.CENTER);  
        this.add(ExibicaoResultados,BorderLayout.CENTER);
        AND.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Campo.setText(Campo.getText()+" & ");
                Campo.requestFocus();
            }
        });
        OR.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Campo.setText(Campo.getText()+" : ");
                Campo.requestFocus();
            }
        });
        NOT.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Campo.setText(Campo.getText()+" !");
                Campo.requestFocus();
            }
        });
        OK.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                SearchAction();
            }
        });
        Campo.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {}

            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_ENTER){
                    SearchAction();
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {}
        });
    }
    private void SearchAction()
    {
        if(chpt.isSelected())
        {
            FirstLang = Language.CHINESE;
            SecondLang = Language.PORTUGUESE;
        }
        else 
        {
            FirstLang = Language.PORTUGUESE;
            SecondLang = Language.CHINESE;
        }
        SearchResultPackage busca = new SearchResultPackage((String)ListaDiretorios.getSelectedItem(),(String)ListaArquivos.getSelectedItem(),Campo.getText(),FirstLang,SecondLang);
        if(busca.getAllResults().isEmpty())
            JOptionPane.showMessageDialog(null, "資料庫裡無您所要查詢的詞句!", "搜尋結果", JOptionPane.WARNING_MESSAGE);
        else
        {
            ExibicaoResultados.clear();
            ExibicaoResultados.setResultPanel(busca);
            JOptionPane.showMessageDialog(null, "已搜尋到 "+busca.getAllResults().size()+" 個相關詞句。", "搜尋結果", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

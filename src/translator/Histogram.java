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
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;

/**
 *
 * @author Julio
 */
public class Histogram extends JPanel{
    public static JPanel PainelProcura = new JPanel(new FlowLayout());
    public static JButton OK = new JButton("OK");
    public static JPanel Resultado = new JPanel();
    public static JPanel Graphic = new JPanel();
    public static JPanel Buttons = new JPanel();
    public static JScrollPane FundoResultado = new JScrollPane(Resultado);
    private Database dados = new Database();
    public static JComboBox ListaDiretorios;
    public static JComboBox ListaArquivos;
    private List<String[]> ListaResultados = new LinkedList<String[]>(); 
    private String[] vetorDir;
    private String[] vetorArq;
    private final int MAX_RESULTS = 30;
    private final String div = " 》";
    private final String GET_ALL_DATA = "61>3_w3_@ll_d474!";
    public JProgressBar[] ProgBar = new JProgressBar[MAX_RESULTS];
    public JButton[] Btn = new JButton[MAX_RESULTS];
    public Histogram()
    {
        this.setLayout(new BorderLayout());        
        List<String> listaDir;
        List<String> listaArq;
        PainelProcura.add(new JLabel("分析在"));
        listaDir = dados.getFolderList();
        listaDir.remove(0);
        vetorDir = new String[listaDir.size()];
        ListaDiretorios = new JComboBox(listaDir.toArray(vetorDir));
        ListaDiretorios.setSelectedIndex(ListaDiretorios.getItemCount()-1);
        PainelProcura.add(ListaDiretorios);
        PainelProcura.add(new JLabel("裡的"));
        listaArq = dados.listOfFiles((String)ListaDiretorios.getSelectedItem());
        listaArq.remove(0);
        vetorArq = new String[listaArq.size()];
        ListaArquivos = new JComboBox(listaArq.toArray(vetorArq));
        PainelProcura.add(ListaArquivos);        
        ListaDiretorios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                List<String> aux = dados.listOfFiles((String)ListaDiretorios.getSelectedItem());
                ListaArquivos.removeAllItems();
                aux.remove(0);
                for(String str: aux)ListaArquivos.addItem(str);             
            }
        });
        PainelProcura.add(OK);
        Resultado.setLayout(new BorderLayout());
        Resultado.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18));
        Graphic.setLayout(new GridLayout(1,MAX_RESULTS));
        Buttons.setLayout(new GridLayout(1,MAX_RESULTS));
//        for(JProgressBar pb: ProgBar){pb = new JProgressBar(JProgressBar.VERTICAL,0,100);Graphic.add(pb);}
//        for(JButton btn: Btn){btn = new JButton(" ");Buttons.add(btn);}
        Resultado.add(Graphic,BorderLayout.CENTER);
        Resultado.add(Buttons,BorderLayout.SOUTH);
        this.add(PainelProcura,BorderLayout.NORTH);
        this.add(FundoResultado,BorderLayout.CENTER);
        OK.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                ListaResultados.addAll((new Search("./data"+((String)ListaDiretorios.getSelectedItem()).replaceAll(div, "/")+"/"+(String)ListaArquivos.getSelectedItem()+".csv",GET_ALL_DATA,Language.CHINESE,Language.PORTUGUESE)).getSearchResult());
                ListaResultados.sort(new Comparator<String[]>() {
                    @Override
                    public int compare(String[] t1, String[] t2) {
                        return Integer.compare(Integer.parseInt(t2[4]),Integer.parseInt(t1[4]));
                    }
                });
                int highest = Integer.parseInt(ListaResultados.get(0)[4]);
                int shortest;
                if(ListaResultados.size()>MAX_RESULTS)shortest = MAX_RESULTS;
                else shortest = ListaResultados.size();
                for(int i=0;i<shortest;i++)
                {
                    ProgBar[i] = new JProgressBar(JProgressBar.VERTICAL,0,100);
                    ProgBar[i].setValue((int)(((float)Integer.parseInt(ListaResultados.get(i)[4])/(float)highest)*100.0));
                    Graphic.add(ProgBar[i]);
                    Btn[i] = new JButton(""+(i+1));
                    Buttons.add(Btn[i]);
                }                        
            }
        });
    }
}

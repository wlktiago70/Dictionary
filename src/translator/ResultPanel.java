/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Julio
 */
public class ResultPanel extends JPanel{
    public static JPanel CenterPanel = new JPanel();
    public static DefaultListModel ListModel = new DefaultListModel();
    public static JList SearchResultList = new JList(ListModel);
    public static JTextArea EnglishTranslation = new JTextArea();
    public static JTextArea SecLangTranslation = new JTextArea();
    public static JTextArea Description = new JTextArea();
    public static JScrollPane SeRePane = new JScrollPane(SearchResultList);
    public static JScrollPane EnTrPane = new JScrollPane(EnglishTranslation);
    public static JScrollPane SeTrPane = new JScrollPane(SecLangTranslation);
    public static JScrollPane DescPane = new JScrollPane(Description);
    public static JLabel SecondLanguage = new JLabel("葡文翻譯：");
    private SearchResultPackage SearchObject;
    public ResultPanel(){
        this.setLayout(new BorderLayout());
        
        EnglishTranslation.setEditable(false);
        SecLangTranslation.setEditable(false);
        Description.setEditable(false);
        EnglishTranslation.setLineWrap(true);
        SecLangTranslation.setLineWrap(true);
        Description.setLineWrap(true);
        EnglishTranslation.setWrapStyleWord(true);
        SecLangTranslation.setWrapStyleWord(true);
        Description.setWrapStyleWord(true);
        
        EnTrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        SeTrPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        DescPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        CenterPanel.setLayout(new GridLayout(3,1));
        JPanel AuxPanel1 = new JPanel();
        AuxPanel1.setLayout(new BorderLayout());
        AuxPanel1.add(new JLabel("英文翻譯："),BorderLayout.NORTH);
        AuxPanel1.add(EnTrPane,BorderLayout.CENTER);
        CenterPanel.add(AuxPanel1);
        JPanel AuxPanel2 = new JPanel();
        AuxPanel2.setLayout(new BorderLayout());
        AuxPanel2.add(SecondLanguage,BorderLayout.NORTH);
        AuxPanel2.add(SeTrPane,BorderLayout.CENTER);
        CenterPanel.add(AuxPanel2);
        JPanel AuxPanel3 = new JPanel();
        AuxPanel3.setLayout(new BorderLayout());
        AuxPanel3.add(new JLabel("參考資料："),BorderLayout.NORTH);
        AuxPanel3.add(DescPane,BorderLayout.CENTER);
        CenterPanel.add(AuxPanel3);
        
        SearchResultList.setLayoutOrientation(JList.VERTICAL);
        SearchResultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //System.out.println(SearchResultList.getCellRenderer());
        SearchResultList.setCellRenderer(new MyCellRenderer(160));
        
        SeRePane.setPreferredSize(new Dimension(250,200));
        this.add(SeRePane,BorderLayout.WEST);
        this.add(CenterPanel,BorderLayout.CENTER);
        SearchResultList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                if(SearchResultList.getSelectedIndex()>=0)
                {
                    SecLangTranslation.setText(SearchObject.getSearchResult().get(SearchResultList.getSelectedIndex())[1]);
                    EnglishTranslation.setText(SearchObject.getSearchResult().get(SearchResultList.getSelectedIndex())[2]);
                    Description.setText(SearchObject.getSearchResult().get(SearchResultList.getSelectedIndex())[3]);
                }
            }
        });
    }
    //(String Folder, String File, String InputExpression)
    public void setResultPanel(SearchResultPackage obj){
        ListModel.removeAllElements();
        SearchObject = obj;
        for(String[] str: SearchObject.getSearchResult()){
            ListModel.addElement(str[0]);
        }
        SecondLanguage.setText(SearchObject.getSecondLanguage().getLanguage()+"翻譯: ");
        //this.repaint();
    }
    public void clear(){
        ListModel.removeAllElements();
        EnglishTranslation.setText("");
        SecLangTranslation.setText("");
        Description.setText("");
    }
    
}

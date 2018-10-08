/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translator;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Julio
 */
public class Search{
    private List<String[]> SearchResult;
    private BufferedReader File;
    private String Line;
    //private final int FirstLangIndex = 1;
    private final String or = ":";
    private final String and = "&";
    private final char not = '!';
    private int count = 1;
    private final String GET_ALL_DATA = "61>3_w3_@ll_d474!";
    public Search(String DataBase, String Expression,Language FirstLang,Language SecondLang){
        SearchResult = new LinkedList<String[]>();
        try {
                File = new BufferedReader(new InputStreamReader(new FileInputStream(DataBase), "Unicode"));
                Line = File.readLine();
        }
        catch (UnsupportedEncodingException ex) {
            System.out.println("Unsupported Encoding Exception");
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (FileNotFoundException ex) {
            System.out.println("File Not Found Exception");   
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            System.out.println("Input / Output Exception");
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(Line != null)
        {
            String[] SplitString = Line.split("	");
            if(SplitString.length > 2 && ExpressionAnalysis(Expression.replaceAll(" ","").trim(), SplitString[FirstLang.getColumnIndex()]))
            {
                String[] aux = new String[5];
                aux[0] = removeQuotationMarks(SplitString[FirstLang.getColumnIndex()]);
                //aux[1] = removeQuotationMarks(SplitString[SplitString.length-1]);
                aux[1] = removeQuotationMarks(SplitString[SecondLang.getColumnIndex()]);
                //aux[2] = removeQuotationMarks(SplitString[SplitString.length-2]);
                aux[2] = removeQuotationMarks(SplitString[Language.ENGLISH.getColumnIndex()]);
                aux[3] = "資料來源: "+getDataSource(DataBase)+"(第"+count+"行)\n"
                        +"更改次數: "+removeQuotationMarks(SplitString[DBEnum.ACCESS_COUNT.getIndex()])+"\n\n"
                        +removeQuotationMarks(SplitString[DBEnum.DESCRIPTION.getIndex()]);
                aux[4] = removeQuotationMarks(SplitString[DBEnum.ACCESS_COUNT.getIndex()]);
                SearchResult.add(aux);
            }            
            try {
                Line = File.readLine();
                count++;
            } catch (IOException ex) {
                Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            File.close();
        } catch (IOException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean ExpressionAnalysis(String Exp, String Src){
        if(Exp.equals(GET_ALL_DATA))return true;
        if(!Exp.matches("[(]")&&!Exp.matches("[)]")){
            String[] AuxStrOr = Exp.split(or);
            boolean AuxBoolOr = false;
            for(int i=0;i<AuxStrOr.length;i++){
                String[] AuxStrAnd = AuxStrOr[i].split(and);
                boolean AuxBoolAnd = true;
                for(int j=0;j<AuxStrAnd.length;j++){
                    if(AuxStrAnd[j].charAt(0) == not)AuxBoolAnd = AuxBoolAnd&&!Src.matches(AuxStrAnd[j].substring(1));
                    else AuxBoolAnd = AuxBoolAnd&&Src.matches(AuxStrAnd[j]);
                }
                AuxBoolOr = AuxBoolOr || AuxBoolAnd;
            }
            return AuxBoolOr;
        }
//        Break the expression into parts, taking OR and AND as parameters, analyse the first character of each part, looking for possibilities as bellow:
//            - charAt(0) == '('
//            - charAt(0) == '!'
//        else if(Exp.charAt(0) == '(')return ExpressionAnalysis(getFirstExpression(Exp.substring(1)),Src);
//        else if(Exp.charAt(0) == not && Exp.charAt(1) == '(')return !ExpressionAnalysis(getFirstExpression(Exp.substring(2)),Src);
//        else if(Exp.charAt(0) == not)return ExpressionAnalysis(Exp.substring(Exp.indexOf(or)+1,Exp.length()),Src)||
//                                          (ExpressionAnalysis(Exp.split(or)[0].substring(Exp.split(or)[0].indexOf(and)+1),Src)&&
//                                          !Src.contains(Exp.substring(1).split(or)[0].split(and)[0]));
//        else return ExpressionAnalysis(Exp.substring(Exp.indexOf(or)+1,Exp.length()),Src)||
//                   (ExpressionAnalysis(Exp.split(or)[0].substring(Exp.split(or)[0].indexOf(and)+1),Src)&&
//                   Src.contains(Exp.substring(1).split(or)[0].split(and)[0]));
        else return false;
    }
//    private String getFirstExpression(String Str){
//        int Cont = 1;
//        for(int i=0;i<Str.length();i++){
//            System.out.println(Str.substring(0, i-1));
//            if(Cont == 0)return Str.substring(0, i-1);
//            else if(Str.charAt(i) == '(')Cont++;
//            else if(Str.charAt(i) == ')')Cont--;
//        }
//        return "";
//    }
    public String removeQuotationMarks(String str){
        if(str.length()>2&&str.charAt(0) == '"')return str.substring(1,str.length()-1);
        return str;
    }
    public List<String[]> getResultList(){
        return SearchResult;
    }
    public List<String[]> getSearchResult(){
        return SearchResult;
    }
    private String getDataSource(String str){
        return str.substring(6, str.length()-4).replaceAll("/", " 》").replaceAll(".*\\.*", " 》");
    }
}

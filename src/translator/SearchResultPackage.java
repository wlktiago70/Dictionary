/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translator;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Julio
 */
public class SearchResultPackage {
    private final String root = "所有類別";
    private final String all = "所有檔案";
    private final String div = " 》";
    private List<String[]> AllResults = new LinkedList<String[]>();
    private final String Exp;
    private Language Lang1;
    private Language Lang2;
    public SearchResultPackage(String Folder,String File,String Expression,Language FirstLang,Language SecondLang)
    {        
        Exp = Expression;
        Lang1 = FirstLang;
        Lang2 = SecondLang;
        if(Folder.equals(root))
        {
            SearchInFolder(new File("./data"));
        }
        else if(File.equals(all))
        {
            SearchInFolder(new File("./data"+Folder.replaceAll(div, "/")));
        }
        else
        {
            AllResults.addAll((new Search("./data"+Folder.replaceAll(div, "/")+"/"+File+".csv",Exp,Lang1,Lang2)).getSearchResult());
        }
    }
    public List<String[]> getSearchResult()
    {
        return AllResults;
    }
    public List<String[]> getAllResults()
    {
        return AllResults;
    }
    public Language getFirstLanguage()
    {
        return Lang1;
    }
    public Language getSecondLanguage()
    {
        return Lang2;
    }
    private void SearchInFolder(final File Folder) {
        for (final File fileEntry : Folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                SearchInFolder(fileEntry);
            } else {
                Search aux = new Search(fileEntry.getPath(),Exp,Lang1,Lang2);
                AllResults.addAll(aux.getSearchResult());
            }
        }
    }
}

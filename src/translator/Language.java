/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translator;

/**
 *
 * @author Julio
 */
public enum Language {
    CHINESE(DBEnum.CHINESE.getIndex(),"中文"),
    ENGLISH(DBEnum.ENGLISH.getIndex(),"英文"),
    PORTUGUESE(DBEnum.PORTUGUESE.getIndex(),"葡文");
    private final int columnIndex;
    private final String language;
    Language(int index,String lang)
    {
        this.columnIndex = index;
        this.language = lang;
    }
    public int getColumnIndex() 
    {
        return columnIndex;
    }
    public String getLanguage()
    {
        return language;
    }
}

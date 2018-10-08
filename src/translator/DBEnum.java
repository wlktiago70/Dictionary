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
public enum DBEnum {
    CHINESE(0),ENGLISH(2),PORTUGUESE(1),DESCRIPTION(3),ACCESS_COUNT(4);
    private final int Index;
    DBEnum(int index)
    {
        this.Index = index;
    }
    public int getIndex() 
    {
        return Index;
    }
}

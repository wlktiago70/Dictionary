/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package translator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Julio
 */
public class Database {
    private List<String> FolderList = new ArrayList(); 
    private List<String> PathList = new ArrayList();
    private final String div = " 》";
    private final String root = "所有類別";
    private final String all = "所有檔案";
    public Database(){
        final File Folder = new File(".\\data");
        FolderList.add(root);
        listFoldersForFolder(Folder,"");
    }
    private void listFoldersForFolder(final File Folder, String path) {
        for (final File fileEntry : Folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                FolderList.add(path+div+fileEntry.getName());
                listFoldersForFolder(fileEntry, path+div+fileEntry.getName());
            }
        }
    }
    public List<String> getFolderList(){
        return FolderList;
    }
    public List<String> listOfFiles(String path){
        List<String> FileList = new ArrayList();
        FileList.add(all);
        //if(path.equals(root))path = "";
        if(path.equals(root))return FileList;
        path = path.replaceAll(div,"/");
        final File Folder = new File(".\\data"+path);        
        listFilesForFolder(Folder,FileList);
        return FileList;
    }
    private void listFilesForFolder(final File Folder, List<String> FileList) {
        for (final File fileEntry : Folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry, FileList);
            } else {
                FileList.add(fileEntry.getName().substring(0, fileEntry.getName().length()-4));
            }
        }
    }
}

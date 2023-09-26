package com.uwu.study.dp.structure.composite;

import java.util.List;

/**
 * 将对象组合成树形结构，表示"部分-整体"层次结构
 * 希望客户端可以忽略组合对象与单个对象盾差异。
 */
public class CompositePatternTest {
    public static void main(String[] args) {
        AbstractFile root = new Folder("root");
        AbstractFile folderA = new Folder("folderA");
        AbstractFile folderB = new Folder("folderB");

        AbstractFile file1 = new File("file1");
        AbstractFile file2 = new File("file2");
        AbstractFile file3 = new File("file3");

        root.add(folderA);
        root.add(folderB);
        root.add(file1);

        folderA.add(file2);
        folderB.add(file3);

        print(root);
    }

    private static void print(AbstractFile root) {
        root.printName();

        List<AbstractFile> childList = root.getChildren();
        if(childList == null) return;

        for(AbstractFile child : childList){
            print(child);
        }
    }
}

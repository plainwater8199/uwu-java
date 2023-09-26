package com.uwu.study.dp.structure.composite;

import java.util.List;

public class File extends AbstractFile{

    public File(String name){
        this.name = name;
    }
    @Override
    public boolean add(AbstractFile file) {
        return false;
    }

    @Override
    public boolean remove(AbstractFile file) {
        return false;
    }

    @Override
    public List<AbstractFile> getChildren() {
        return null;
    }
}

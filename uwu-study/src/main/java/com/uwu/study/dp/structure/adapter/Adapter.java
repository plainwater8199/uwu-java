package com.uwu.study.dp.structure.adapter;

public class Adapter extends USB{
    private TypeC typeC = new TypeC();

    @Override
    public void Request(){
        typeC.SpecificRequest();
    }
}

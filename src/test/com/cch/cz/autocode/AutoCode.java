package com.cch.cz.autocode;

import com.cch.autocode.GenerateBase;
import com.cch.cz.entity.AdjustLog;

public class AutoCode {

    public static void main(String[] args) {
        GenerateBase.generateController(new AdjustLog());
    }


}


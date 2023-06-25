package com.ruoshui.market.factory;


import com.ruoshui.market.enums.CipherType;

public class FactoryProducer {

    public static AbstractFactory getFactory(String type){
        CipherType cipherType = CipherType.getCipherType(type);
        switch (cipherType) {
            case REGEX:
                return new RegexFactory();
            case ALGORITHM:
                return new AlgorithmFactory();
        }
        return null;
    }
}

package com.ruoshui.market.factory;


import com.ruoshui.market.enums.AlgorithmCrypto;
import com.ruoshui.market.factory.crypto.AlgorithmRegistry;
import com.ruoshui.market.factory.crypto.Crypto;

public class AlgorithmFactory extends AbstractFactory {

    private static final AlgorithmRegistry ALGORITHM_REGISTRY = new AlgorithmRegistry();

    @Override
    public Crypto getCrypto(String type) {
        AlgorithmCrypto crypto = AlgorithmCrypto.getAlgorithmCrypto(type);
        return ALGORITHM_REGISTRY.getAlgorithm(crypto);
    }
}

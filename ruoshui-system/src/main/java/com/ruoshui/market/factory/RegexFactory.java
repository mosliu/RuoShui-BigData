package com.ruoshui.market.factory;


import com.ruoshui.market.enums.RegexCrypto;
import com.ruoshui.market.factory.crypto.RegexRegistry;
import com.ruoshui.market.factory.crypto.Crypto;


public class RegexFactory extends AbstractFactory {

    private static final RegexRegistry REGEX_REGISTRY = new RegexRegistry();

    @Override
    public Crypto getCrypto(String type) {
        RegexCrypto crypto = RegexCrypto.getRegexCrypto(type);
        return REGEX_REGISTRY.getRegex(crypto);
    }
}

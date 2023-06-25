package com.ruoshui.market.factory;

import com.ruoshui.market.factory.crypto.Crypto;

public abstract class AbstractFactory {

    public abstract Crypto getCrypto(String type);
}

package com.imooc.miaosha.redis;

public abstract class BasePrefix implements KeyPrefix{
    private int expireSecond;
    private String prefix;
    public BasePrefix(int expireSecond, String prefix) {

        this.expireSecond = expireSecond;
        this.prefix = prefix;
    }
    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    @Override
    public int expireSeconds() {
        return expireSecond;
    }

    @Override
    public String getPrefix() {
        return getClass().getSimpleName()+":"+prefix;
    }
}

package practice.smartmvc.chp4;

public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}

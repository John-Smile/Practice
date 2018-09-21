package practice.rpcfs.chp4;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class AresRemoteReferenceNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("reference", new RevokerFactoryBeanDefinitionParser());
    }
}

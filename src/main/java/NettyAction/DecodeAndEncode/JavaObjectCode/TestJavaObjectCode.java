package NettyAction.DecodeAndEncode.JavaObjectCode;

import NettyAction.TimeServerAndClient.democlient.NettyClient;
import NettyAction.TimeServerAndClient.demoserver.NettyServer;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.junit.Test;

/**
 * ���Ի���java���л������ݵı������
 */
public class TestJavaObjectCode {

    //���������
    @Test
    public void startMeterServer(){

        //�ͻ�����������˵Ķ�������л��ͷ����л�����netty�Ķ������������

        ObjectDecoder objectDecoder=new ObjectDecoder(1024*1024,
                ClassResolvers.weakCachingResolver(this.getClass().getClassLoader()));

        new NettyServer()
                .initServerAndBindPort(8005)
                .addChildHandlers(objectDecoder,new ObjectEncoder(),new RtuMeterServerHandler())
                .start();

    }

    //�����豸
    @Test
    public void startMeterClient(){

        ObjectDecoder objectDecoder=new ObjectDecoder(1024,
                ClassResolvers.cacheDisabled(this.getClass().getClassLoader()));

        new NettyClient()
                .connect("localhost",8005)
                .addHandlers(objectDecoder,new ObjectEncoder(),new RtuMeterClientHandler())
                .start();
    }
}

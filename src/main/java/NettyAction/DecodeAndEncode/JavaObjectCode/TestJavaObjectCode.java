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

    public static void main(String[] args) {
        //�ͻ�����������˵Ķ�������л��ͷ����л�����netty�Ķ������������


        new NettyServer()
                .initServerAndBindPort(8005)
                .addChildHandlers(new ObjectDecoder(1024*1024,
                        ClassResolvers.weakCachingResolver(TestJavaObjectCode.class.getClassLoader())),new ObjectEncoder(),new RtuMeterServerHandler())
                .start();


    }

    //���������
    @Test
    public void startMeterServer(){

        //�ͻ�����������˵Ķ�������л��ͷ����л�����netty�Ķ������������


        new NettyServer()
                .initServerAndBindPort(8005)
                .addChildHandlers(new ObjectDecoder(1024*1024,
                        ClassResolvers.weakCachingResolver(this.getClass().getClassLoader())),new ObjectEncoder(),new RtuMeterServerHandler())
                .start();

    }

    //�����豸
    @Test
    public void startMeterClient(){

        new NettyClient()
                .connect("localhost",8005)
                .addHandlers(new ObjectDecoder(1024,
                        ClassResolvers.cacheDisabled(this.getClass().getClassLoader())),new ObjectEncoder(),new RtuMeterClientHandler())
                .start();
    }

    //�����豸
    @Test
    public void startMeterClient2(){

        ObjectDecoder objectDecoder=new ObjectDecoder(1024,
                ClassResolvers.cacheDisabled(this.getClass().getClassLoader()));

        new NettyClient()
                .connect("localhost",8005)
                .addHandlers(objectDecoder,new ObjectEncoder(),new RtuMeterClientHandler())
                .start();
    }
}

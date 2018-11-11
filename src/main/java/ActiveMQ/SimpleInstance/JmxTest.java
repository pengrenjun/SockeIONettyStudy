package ActiveMQ.SimpleInstance;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @Description:
 * @Author��pengrj
 * @Date : 2018/11/10 0010 21:21
 * @version:1.0
 */
public class JmxTest {

    public static void main(String[] args) throws IOException, MalformedObjectNameException {

        String url = "service:jmx:rmi:///jndi/rmi://localhost:11099/jmxrmi";
        JMXServiceURL urls = new JMXServiceURL(url);
        JMXConnector connector = JMXConnectorFactory.connect(urls,null);
        connector.connect();
        MBeanServerConnection conn = connector.getMBeanServerConnection();

//����brokerName��bҪСЩ����д�ᱨ��
        ObjectName name = new ObjectName("myDomain:brokerName=broker,type=Broker");
        BrokerViewMBean mBean = (BrokerViewMBean) MBeanServerInvocationHandler.newProxyInstance
                (conn, name, BrokerViewMBean.class, true);
        for(ObjectName na : mBean.getQueues()){
            QueueViewMBean queueBean = MBeanServerInvocationHandler.newProxyInstance
                    (conn, na, QueueViewMBean.class, true);
            System.out.println("******************************");
            System.out.println("���е����ƣ�"+queueBean.getName());
            System.out.println("������ʣ�����Ϣ����"+queueBean.getQueueSize());
            System.out.println("����������"+queueBean.getConsumerCount());
            System.out.println("�����е�������"+queueBean.getDequeueCount());
        }
    }

}

package ActiveMQ.ActiveMqJmx;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:  ActiveMq启用Jmx 通过程序对监控平台的数据进行监控
 * @Author：pengrj
 * @Date : 2018/11/10 0010 21:21
 * @version:1.0
 */
public class JmxTest {

    public static void main(String[] args) throws IOException, MalformedObjectNameException {

           List<MqModel> mqModelList= testJmxActiveMq();

           System.out.println(mqModelList.toString());
        }


        public static List<MqModel> testJmxActiveMq() throws IOException, MalformedObjectNameException {
              //获取mq远程链接地址
              final String MQ_URL ="service:jmx:rmi:///jndi/rmi://localhost:11099/jmxrmi";
              //mq用户名
              final String MQ_USERNAME ="admin" ;
              //mq密码
              final String MQ_USERPWD = "admin";

            Map<String, String[]> env = new HashMap<>();
            String[] credentials = {MQ_USERNAME, MQ_USERPWD};
            env.put(JMXConnector.CREDENTIALS, credentials);
            JMXServiceURL url = new JMXServiceURL(MQ_URL);

            JMXConnector jmxc = JMXConnectorFactory.connect(url, env);
            // JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
            MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
            ObjectName mbeanName = new ObjectName("org.apache.activemq:brokerName=localhost,type=Broker");
            //MBeanInfo info = mbsc.getMBeanInfo(mbeanName);
            BrokerViewMBean mbean =(BrokerViewMBean) MBeanServerInvocationHandler.newProxyInstance(mbsc, mbeanName, BrokerViewMBean.class, true);
            List<MqModel> mqList = new ArrayList<MqModel>();
            //OrganizationService organizationService = (OrganizationService) SpringBeanUtil.getBean("organizationService");

            for (ObjectName na : mbean.getQueues()) {
                QueueViewMBean queueBean = (QueueViewMBean)MBeanServerInvocationHandler.newProxyInstance(mbsc, na, QueueViewMBean.class, true);
                MqModel mq = new MqModel();
                mq.setName(queueBean.getName());
                mq.setQueueSize(queueBean.getQueueSize());
                mq.setConsumerCount(queueBean.getConsumerCount());
                mq.setDequeueCount(queueBean.getDequeueCount());
                mq.setEnqueueCount(queueBean.getEnqueueCount());
                queueBean.getEnqueueCount();
                mqList.add(mq);
            }

            return mqList;

        }
    }



package ActiveMQ.SimpleInstance;

import ActiveMQ.ActiveMqJmx.JmxTest;
import ActiveMQ.ActiveMqJmx.MqModel;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.*;
import javax.management.MalformedObjectNameException;
import java.io.IOException;
import java.sql.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;
import static javax.jms.Session.CLIENT_ACKNOWLEDGE;

/**
 * @Description: ActiveMq��ʵ��:������
 * @Author��pengrj
 * @Date : 2018/11/10 0010 19:20
 * @version:1.0
 */
public class Customer {

    //��ActiveMq��Ϣ�м��ƽ̨��ȡ��������
    public  void startCustom() throws JMSException, InterruptedException, IOException, MalformedObjectNameException {

        //1.����ConnectionFactory��������

        ConnectionFactory connectionFactory=new ActiveMQConnectionFactory("tcp://localhost:61616");

        //2.ͨ��ConnectionFactory����һ������ ָ����֤���û���������,������������
        Connection connection=connectionFactory.createConnection(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD);
        connection.start();

        //3.ͨ������������connection����session�Ự(���ӵ������Ļ���),���ڽ�����Ϣ,����1���Ƿ��������� ����2���趨ǩ��ģʽ һ����Ϊ�Զ�ǩ��
                /*AUTO_ACKNOWLEDGE
                CLIENT_ACKNOWLEDGE
                DUPS_OK_ACKNOWLEDGE*/

        //��Ҫͨ��textMessage.acknowledge();����ƽ̨����
        Session textSession=connection.createSession(false,CLIENT_ACKNOWLEDGE);
        //���Ѷ���Ҫsession.commit���ܸ���MQ�������˵���ʾ����
        Session mapSession=connection.createSession(true,AUTO_ACKNOWLEDGE);

        //4.ͨ��Session���� Destination ��������ָ��������ϢĿ���������ԴĿ��
        //  �ڵ�Ե�PTP��  Destination��ΪQueue ������  ��Pub/Sub�� ����Ϊ����Topic �ɴ���ָ��������к�����

        Destination destination=textSession.createQueue("TextQueue");

        Destination destination1=mapSession.createQueue("MapQueue");


        //5.ͨ��Session���󴴽���Ϣ��������������

        MessageConsumer messageConsumer=textSession.createConsumer(destination);
        MessageConsumer messageConsumer1=mapSession.createConsumer(destination1);

        //6.���������ߵ��������ݵĳ־û�����

        //7.�������� �����߷������� �����߽������� �ر�connection ����



           while(true){

               List<MqModel> mqModels=JmxTest.testJmxActiveMq();
               MqModel textQueueModel=null;
               MqModel mapQueueModel=null;
               for(MqModel mqModel:mqModels){

                   if(mqModel.getName().equals("TextQueue")){
                       textQueueModel=mqModel;
                   }

                   if(mqModel.getName().equals("MapQueue")){
                       mapQueueModel=mqModel;
                   }
               }

               if(textQueueModel!=null && textQueueModel.getQueueSize()>0){
                   TextMessage textMessage= (TextMessage) messageConsumer.receive();
                   //�ֶ�ȷ��ǩ�� �����̸߳���Mqƽ̨����ʾ���� ���������session�����
                   textMessage.acknowledge();

                   System.out.println("TextQueue:"+textMessage.getText());

               }
               if(mapQueueModel!=null && mapQueueModel.getQueueSize()>0){
                   MapMessage mapMessage= (MapMessage) messageConsumer1.receive();
                   System.out.println("MapQueue>>>>>>>>>>>>"+mapMessage.toString());

                   mapSession.commit();
               }

               if(textQueueModel.getQueueSize() ==0 && mapQueueModel.getQueueSize()==0){
                   break;
               }

           }

        connection.close();
        System.out.println("Customer has customed Messages ok!");
    }

    public static void main(String[] args) throws JMSException, InterruptedException, IOException, MalformedObjectNameException {
        Customer customer=new Customer();
        customer.startCustom();
    }
}

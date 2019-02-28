package practice.rocketmqinaction.chp6;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class CustomMessageQueueSelector implements MessageQueueSelector {
    @Override
    public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
        for (int i = 0; i < 100; ++i) {
            int orderId = i;
            String tags = null;
            Message msg = null;
            try {
                msg = new Message("OrderTopic8", tags, "KEY" + i, ("Hello RocketMQ " + orderId + " " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            MQProducer producer = new DefaultMQProducer();
            SendResult sendResult = null;
            try {
                sendResult = producer.send(msg, (MessageQueueSelector) (mqs, message1, arg) -> {
                    System.out.println("queue selector mq nums:" + mqs.toString());
                    for (MessageQueue mq : mqs) {
                        System.out.println(mq.toString());
                    }
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }, orderId);
            } catch (MQClientException e) {
                e.printStackTrace();
            } catch (RemotingException e) {
                e.printStackTrace();
            } catch (MQBrokerException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(sendResult);
        }
        return null;
    }

    public void foo() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
        consumer.registerMessageListener((MessageListenerOrderly) (msgs, consumeOrderlyContext) -> {
            System.out.println("Received New Message: " + new String(msgs.get(0).getBody()) + "%n");
            return ConsumeOrderlyStatus.SUCCESS;
        });
    }
}

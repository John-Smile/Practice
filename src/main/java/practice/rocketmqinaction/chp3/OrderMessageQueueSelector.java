package practice.rocketmqinaction.chp3;

import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class OrderMessageQueueSelector implements MessageQueueSelector {
    @Override
    public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
        int id = Integer.parseInt(o.toString());
        int idMainIndex = id / 100;
        int size = list.size();
        int index = idMainIndex % size;
        return list.get(index);
    }
}
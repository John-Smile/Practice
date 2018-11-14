package practice.rocketmqinaction.chp3;

import org.apache.rocketmq.client.consumer.store.OffsetSerializeWrapper;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.common.message.MessageQueue;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class LocalOffsetStoreExt {
    private final String groupName;
    private final String storePath;
    private ConcurrentHashMap<MessageQueue, AtomicLong> offsetTable = new ConcurrentHashMap<>();

    public LocalOffsetStoreExt(String groupName, String storePath) {
        this.groupName = groupName;
        this.storePath = storePath;
    }
    public void load() {
        OffsetSerializeWrapper offsetSerializeWrapper = this.readLocalOffset();
        if (offsetSerializeWrapper != null
                && offsetSerializeWrapper.getOffsetTable() != null) {
            offsetTable.putAll(offsetSerializeWrapper.getOffsetTable());
            for (MessageQueue mq : offsetSerializeWrapper.getOffsetTable().keySet()) {
                AtomicLong offset = offsetSerializeWrapper.getOffsetTable().get(mq);
                System.out.printf("load Consumer's Offset, %s %s %s\n", this.groupName, mq, offset.get());
            }
        }
    }

    public long readOffset(final MessageQueue mq) {
        if (mq != null) {
            AtomicLong offset = this.offsetTable.get(mq);
            if (offset != null) {
                return offset.get();
            }
        }
        return 0;
    }

    public void persistAll(Set<MessageQueue> mqs) {
        if (null == mqs
                || mqs.isEmpty()) {
            return;
        }
        OffsetSerializeWrapper offsetSerializeWrapper = new OffsetSerializeWrapper();
        for (Map.Entry<MessageQueue, AtomicLong> entry : this.offsetTable.entrySet()) {
            if (mqs.contains(entry.getKey())) {
                AtomicLong offset = entry.getValue();
                offsetSerializeWrapper.getOffsetTable().put(entry.getKey(), entry.getValue());
            }
        }
        String jsonString = offsetSerializeWrapper.toJson(true);
        if (jsonString != null) {
            try {
                MixAll.string2File(jsonString, this.storePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private OffsetSerializeWrapper readLocalOffset() {
        String content = null;
        try {
            content = MixAll.file2String(this.storePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null == content
                || content.length() == 0) {
            return null;
        } else {
            OffsetSerializeWrapper offsetSerializeWrapper = null;
            try {
                offsetSerializeWrapper = OffsetSerializeWrapper.fromJson(content, OffsetSerializeWrapper.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return offsetSerializeWrapper;
        }
    }

    public void updateOffset(MessageQueue mq, long offset) {
        if (mq != null) {
            AtomicLong offsetOld = this.offsetTable.get(mq);
            if (null == offsetOld) {
                this.offsetTable.putIfAbsent(mq, new AtomicLong(offset));
            } else {
                offsetOld.set(offset);
            }
        }
    }
}

package ru.grishchenko.consumer;


public class MessageObject {

    public enum MsgType {

        UNDEFINED(" "),
        SUBSCRIBE("set_topic"),
        UNSUBSCRIBE("unset_topic"),
        EXIT("exit");

        private String msgSignature;

        public String getMsgSignature() {
            return msgSignature;
        }

        public void setMsgSignature(String msgSignature) {
            this.msgSignature = msgSignature;
        }

        MsgType(String msgSignature) {
            this.msgSignature = msgSignature;
        }
    }

    private final String originMessage;
    private final MsgType type;
    private final String key;

    public MessageObject(String message) {
        this.originMessage = message.trim();
        this.type = buildType(originMessage);
        this.key = buildKey(originMessage);
    }

    private MsgType buildType(String message) {
        for (MsgType type : MsgType.values()) {
            if (message.startsWith(type.getMsgSignature())) {
                return type;
            }
        }
        return MsgType.UNDEFINED;
    }

    private String buildKey(String message) {
        String[] tmp = message.split("\\s+");
        if (tmp.length <= 1) {  // если в строке нет пробела или строка пустая
            return "";
        }
        return tmp[1];
    }

    public MsgType getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getOriginMessage() {
        return originMessage;
    }
}
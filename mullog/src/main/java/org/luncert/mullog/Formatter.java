package org.luncert.mullog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Formatter {

    private static final String RE_FORMAT_STRING = "%[T|L|M|C|S]";
    
    private SlotChain slotChain = new SlotChain();

    private static enum SlotType {
        Timestamp, LogLevel, MethodName, ClassName, PlainText, Message
    }

    private static class Slot {
        SlotType type;
        String content;
        Slot next;
        Slot() {}
        Slot(SlotType type, String content) {
            this.type = type;
            this.content = content;
        }
    }

    private static class SlotChain {
        Slot head, tail;
        void addSlot(Slot slot) {
            if (head == null)
                head = tail = slot;
            else
                tail = tail.next = slot;
        }
    }

    public Formatter(String formatString) {
        Pattern r = Pattern.compile(RE_FORMAT_STRING);
        Matcher matcher = r.matcher(formatString);
        Slot slot;
        for (int lastMatch = 0; matcher.find();) {
            String part = matcher.group(0);
            if (lastMatch != matcher.start()) {
                slot = new Slot(SlotType.PlainText,
                                formatString.substring(lastMatch, matcher.start()));
                slotChain.addSlot(slot);
            }
            char c = part.charAt(1);
            slot = new Slot();
            if (c == 'T')
                slot.type = SlotType.Timestamp;
            else if (c == 'L')
                slot.type = SlotType.LogLevel;
            else if (c == 'M')
                slot.type = SlotType.MethodName;
            else if (c == 'C')
                slot.type = SlotType.ClassName;
            else
                slot.type = SlotType.Message;
            slotChain.addSlot(slot);
            lastMatch = matcher.end();
        }
    }

    public String format(int logLevel, String ... fields) {
        StringBuilder builder = new StringBuilder();
        int i = 0, limit = fields.length;
        Slot slot = slotChain.head;
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[4];
        while (slot != null) {
            if (slot.type.equals(SlotType.LogLevel))
                builder.append(LogLevel.convertString(logLevel));
            else if (slot.type.equals(SlotType.Message)) {
                if (i < limit)
                    builder.append(fields[i++]);
            }
            else if (slot.type.equals(SlotType.MethodName))
                builder.append(stackTraceElement.getMethodName());
            else if (slot.type.equals(SlotType.ClassName)) {
                String className = stackTraceElement.getClassName();
                className = className.substring(className.lastIndexOf(".") + 1);
                builder.append(className);
            }
            else if (slot.type.equals(SlotType.Timestamp))
                builder.append(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
            else builder.append(slot.content);
            slot = slot.next;
        }
        return builder.toString();
    }

}
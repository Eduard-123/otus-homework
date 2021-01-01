package ru.otus.processor;

import ru.otus.Message;

public class LoggerProcessor implements Processor {

    private final ru.otus.processor.Processor processor;

    public LoggerProcessor(ru.otus.processor.Processor processor) {
        this.processor = processor;
    }

    @Override
    public Message process(Message message) {
        System.out.println("log processing message:" + message);
        return processor.process(message);
    }
}

package ru.otus.listener;


import ru.otus.Message;

import java.util.LinkedList;
import java.util.List;

public class ListenerLogbook implements Logbook {

    private final List<Record> records;

    public ListenerLogbook() {
        this.records = new LinkedList<>();
    }

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        addRecord(oldMsg, newMsg);
    }

    @Override
    public void addRecord(Message oldMassage, Message newMessage) {
        records.add(new ru.otus.listener.Record(oldMassage.clone(), newMessage.clone()));
    }

    @Override
    public List<ru.otus.listener.Record> getRecords() {
        return new LinkedList<>(records);
    }
}

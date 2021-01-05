package ru.otus.listener;


import ru.otus.Message;

public class ListenerPrinter implements ru.otus.listener.Listener {

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        var logString = String.format("oldMsg:%s, newMsg:%s", oldMsg, newMsg);
        System.out.println(logString);
    }
}
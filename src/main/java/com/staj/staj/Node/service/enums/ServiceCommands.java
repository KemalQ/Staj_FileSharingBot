package com.staj.staj.Node.service.enums;

public enum ServiceCommands {
    HELP("/help"),
    REGISTRATION("/registration"),
    CANCEL("/cancel"),
    START("/start");
    private String cmd;
    
    ServiceCommands(String cmd){
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return cmd;
    }
    public boolean equals(String cmd){//сравнение двух команд
        return this.toString().equals(cmd);
    }
}

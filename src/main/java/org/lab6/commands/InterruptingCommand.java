package org.lab6.commands;

import org.lab6.mainClasses.ClientCommandManager;

public interface InterruptingCommand {
        void setThread(ClientCommandManager clientThread);
}

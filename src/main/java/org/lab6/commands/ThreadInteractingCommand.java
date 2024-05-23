package org.lab6.commands;

import org.lab6.mainClasses.ClientCommandManager;

public interface ThreadInteractingCommand {
        void setThread(ClientCommandManager clientThread);
}

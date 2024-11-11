package ch.supsi.editor2d.command;

import ch.supsi.editor2d.contracts.receiver.Receiver;

public abstract class AbstractCommand<T extends Receiver> implements Command
{
    protected T receiver;

    protected AbstractCommand(T receiver){
        this.receiver = receiver;
    }


}

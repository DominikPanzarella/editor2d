package ch.supsi.editor2d.command;

import ch.supsi.editor2d.contracts.handler.AboutHandler;
import ch.supsi.editor2d.contracts.receiver.AboutReceiver;

public class AboutCommand<T extends AboutReceiver<AboutHandler>> extends AbstractCommand<T>
{

    protected AboutCommand(T aboutReceiver){
        super(aboutReceiver);
    }

    public static <T extends AboutReceiver<AboutHandler>> AboutCommand<T> create(T receiver) throws InstantiationException {
        if (receiver == null)
            throw new InstantiationException("Command receiver cannot be null!");
        return new AboutCommand<>(receiver);
    }

    @Override
    public void execute() {
        receiver.about();
    }
}

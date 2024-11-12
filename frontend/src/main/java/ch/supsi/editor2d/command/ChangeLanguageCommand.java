package ch.supsi.editor2d.command;

import ch.supsi.editor2d.contracts.handler.ChangeLanguageHandler;
import ch.supsi.editor2d.contracts.receiver.ChangeLanguageReceiver;

public class ChangeLanguageCommand<T extends ChangeLanguageReceiver<ChangeLanguageHandler>> extends AbstractCommand<T> {
    private final String languageTag;
    protected ChangeLanguageCommand(T receiver, String languageTag) {
        super(receiver);
        this.languageTag = languageTag;
    }

    public static <T extends ChangeLanguageReceiver<ChangeLanguageHandler>> ChangeLanguageCommand<T> create(T receiver, String languageTag) throws InstantiationException {
        if(receiver == null)
            throw new InstantiationException("Command receiver cannot be null!");
        return new ChangeLanguageCommand<>(receiver, languageTag);
    }

    @Override
    public void execute() {
        receiver.changeLanguage(this.languageTag);
    }
}

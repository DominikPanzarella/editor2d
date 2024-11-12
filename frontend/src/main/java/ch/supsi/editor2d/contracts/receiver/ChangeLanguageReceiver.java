package ch.supsi.editor2d.contracts.receiver;

import ch.supsi.editor2d.contracts.handler.ChangeLanguageHandler;

public interface ChangeLanguageReceiver <T extends ChangeLanguageHandler> extends Receiver{

    void changeLanguage(String languageTag);
}

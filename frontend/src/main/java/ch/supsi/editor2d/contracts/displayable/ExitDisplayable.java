package ch.supsi.editor2d.contracts.displayable;

import ch.supsi.editor2d.command.CancelCommand;
import ch.supsi.editor2d.command.OkCommand;
import ch.supsi.editor2d.contracts.handler.CancelHandler;
import ch.supsi.editor2d.contracts.handler.OKHandler;
import ch.supsi.editor2d.contracts.receiver.CancelReceiver;
import ch.supsi.editor2d.contracts.receiver.OkReceiver;

public interface ExitDisplayable {
    <T extends OkCommand<? extends OkReceiver<OKHandler>>> void createOKBehaviour(T command);
    <T extends CancelCommand<? extends CancelReceiver<CancelHandler>>> void createCancelBehaviour(T command);
}

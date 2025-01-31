package ch.supsi.editor2d.contracts.displayable;

import ch.supsi.editor2d.command.*;
import ch.supsi.editor2d.contracts.handler.*;
import ch.supsi.editor2d.contracts.receiver.*;

public interface MenuDisplayable {
    <T extends OpenFileCommand<? extends OpenFileReceiver<OpenFileHandler>>> void createOpenMenuItemBehaviour(T command);

    <T extends AboutCommand<? extends AboutReceiver<AboutHandler>>> void createAboutBehavior(T command);

    <T extends ChangeLanguageCommand<? extends ChangeLanguageReceiver<ChangeLanguageHandler>>> void createEnUSMenuItemBehaviour(T command);

    <T extends ChangeLanguageCommand<? extends ChangeLanguageReceiver<ChangeLanguageHandler>>> void createItCHMenuItemBehaviour(T command);

    <T extends RunPipelineCommand<? extends RunPipelineReceiver<RunPipelineHandler>>> void createRunPipelineBehaviour(T command);

    <T extends ZoomOutCommand<? extends ZoomOutReceiver<ZoomOutHandler>>> void createZoomOutBehaviour(T command);

    <T extends ZoomInCommand<? extends ZoomInReceiver<ZoomInHandler>>> void createZoomInBehaviour(T command);

    <T extends ExitCommand<? extends ExitReceiver<ExitHandler>>> void createExitBehaviour(T command);

    <T extends ExportFileCommand<? extends ExportFileReceiver<ExportFileHandler>>> void createExportFileBehaviour(T command);

    <T extends MoreInfoCommand<? extends MoreInfoReceiver<MoreInfoHandler>>> void createMoreInfoBehaviour(T command);

    <T extends UndoCommand<? extends UndoReceiver<UndoHandler>>> void createUndoBehaviour(T command);

    <T extends RedoCommand<? extends RedoReceiver<RedoHandler>>> void createRedoBehaviour(T command);
}

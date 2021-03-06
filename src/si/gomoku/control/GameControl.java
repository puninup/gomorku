package si.gomoku.control;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import si.gomoku.actions.SetUpTimer;
import si.gomoku.actions.StartNewGame;
import si.gomoku.actions.StopGame;
import si.gomoku.game.Game;
import si.gomoku.game.GameObserver;
import si.gomoku.game.Stone;

/**
 * @author Tomasz Urbas
 */
public class GameControl implements GameObserver {

    private Game game;

    public GameControl(Game game) {
        this.game = game;
        this.game.addObserver(this);
    }

    @Override
    public void nextTurn(Stone moving) {
        hideWinnerLabelIfViewExists();
        showTimerIfViewExists();
    }

    @Override
    public void endGame(Stone winner) {
        showWinnerLabelIfViewExists(winner);
    }

    @Override
    public void stopGame() {
        showTimerSetterIfViewExists();
    }

    // VIEW ----------------------
    private HBox view;

    private void showWinnerLabelIfViewExists(Stone stone) {
        if (view == null) {
            return;
        }

        String labelText = (stone == null) ? "REMIS" : String.format("ZWYCIĘZCA: Gracz %s", stone.toString());
        Label label = new Label(labelText);
        changeLabel(label);
    }

    private void hideWinnerLabelIfViewExists() {
        if (view == null) {
            return;
        }

        Label label = new Label();
        changeLabel(label);
    }

    private void showTimerSetterIfViewExists() {
        TextField timerSetter = new TextField("1:00");
        timerSetter.focusedProperty().addListener(new SetUpTimer(game, timerSetter));

        Platform.runLater(() -> {
            view.getChildren().remove(6);
            view.getChildren().add(6, timerSetter);
        });
    }

    private void showTimerIfViewExists() {
        Label timerLabel = new Label("CountDownTimer");
        timerLabel.textProperty().bind(game.getTimerStringTime());
        timerLabel.setFont(Font.font("Cambria", 23));

        Platform.runLater(() -> {
            view.getChildren().remove(6);
            view.getChildren().add(6, timerLabel);
        });
    }

    private void changeLabel(Label label) {
        label.setFont(Font.font("Cambria", 23));
        Platform.runLater(() -> {
            view.getChildren().remove(3);
            view.getChildren().add(3, label);
        });
    }

    private void setUpView() {
        Button newGame = new Button("Nowa gra");
        newGame.setOnMouseClicked(new StartNewGame(game));

        Button stopGame = new Button("Zatrzymaj grę");
        stopGame.setOnMouseClicked(new StopGame(game));

        Pane space = new Pane();
        HBox.setHgrow(space, Priority.ALWAYS);

        Label winningLabel = new Label();

        Pane space2 = new Pane();
        HBox.setHgrow(space2, Priority.ALWAYS);

        Label timerLabel = new Label("Pozostały czas: ");
        timerLabel.setFont(Font.font("Cambria", 18));
        HBox timer = new HBox();

        view = new HBox(newGame, stopGame, space, winningLabel, space2, timerLabel, timer);
        view.setPadding(new Insets(0, 0, 10, 0));
        view.setSpacing(10);
    }

    public HBox setUpAndGetView() {
        setUpView();
        showTimerSetterIfViewExists();
        return view;
    }
}

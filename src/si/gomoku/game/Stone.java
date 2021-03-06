package si.gomoku.game;

import javafx.scene.paint.Color;

/**
 * @author Tomasz Urbas
 */
public enum Stone {
    DARK(Color.BLACK) {
        @Override
        public Stone opposite() {
            return Stone.LIGHT;
        }

        @Override
        public String toString() {
            return "czarny";
        }
    },
    LIGHT(Color.WHITE) {
        @Override
        public Stone opposite() {
            return Stone.DARK;
        }

        @Override
        public String toString() {
            return "biały";
        }
    },
    NONE(Color.TRANSPARENT) {
        @Override
        public Stone opposite() {
            return Stone.NONE;
        }
    };

    Color color;

    Stone(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract Stone opposite();
}

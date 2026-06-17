package noncontroller.interfaces;

public interface ISimulationConfig {
    double MIN_CANVAS_WIDTH = 0;
    double MIN_CANVAS_HEIGHT = 0;
    double MAX_CANVAS_WIDTH = 800;
    double MAX_CANVAS_HEIGHT = MAX_CANVAS_WIDTH; // 1:1 Ratio

    int HARD_ENTITY_CAP = 300;

    double GLOBAL_TICK_RATE = 0.2;
}

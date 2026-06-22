package models.interfaces;

public interface ISimulationConfig {
    // Canvas Size Adjuster
    double MIN_CANVAS_WIDTH = 0;
    double MIN_CANVAS_HEIGHT = 0;
    double MAX_CANVAS_WIDTH = 1000;
    double MAX_CANVAS_HEIGHT = MAX_CANVAS_WIDTH; // 1:1 Ratio

    // Entity Cap
    int HARD_ENTITY_CAP = 300;

    // Tick Rate
    double GLOBAL_TICK_RATE = 0.2;
}

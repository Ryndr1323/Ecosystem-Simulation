package noncontroller.utils;

import java.util.ArrayList;
import java.util.List;
import noncontroller.models.Entity;
import noncontroller.interfaces.ISimulationConfig;

public final class DataPool {

    // Kumpulan seluruh entitas aktif yang ada di dalam simulasi (RAM Cache)
    private static final List<Entity> cachePool = new ArrayList<>();

    // Private constructor agar kelas ini tidak bisa diinstansiasi menggunakan keyword 'new'
    private DataPool() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static void addToCache(Entity e) {
        if (e == null) return;

        // 🔒 PROTEKSI POPULASI: Ambil batasan maksimal dari ISimulationConfig
        if (cachePool.size() >= ISimulationConfig.HARD_ENTITY_CAP) {
            return;
        }

        cachePool.add(e);
    }

    public static List<Entity> getCache() {
        return cachePool;
    }

    public static Entity getRandomEntityFromCache() {
        if (cachePool.isEmpty()) {
            return null;
        }

        int randomIndex = Randomizer.getRandomInt(0, cachePool.size() - 1);
        return cachePool.get(randomIndex);
    }

    public static void clearCache() {
        cachePool.clear();
    }

    public static int getPopulationSize() {
        return cachePool.size();
    }
}
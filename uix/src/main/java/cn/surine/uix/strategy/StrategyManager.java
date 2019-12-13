package cn.surine.uix.strategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Intro：
 *
 * @author sunliwei
 * @date 2019-12-11 17:08
 */
public class StrategyManager {

    List<UiStrategy> uiStrategies = new ArrayList<>();

    static class INS {
        public static StrategyManager strategyManager = new StrategyManager();
    }

    private StrategyManager() {
        uiStrategies.add(new FontStrategy());
        uiStrategies.add(new ThemeStrategy());
        uiStrategies.add(new ViewStrategy());
    }

    public static StrategyManager getInstance() {
        return INS.strategyManager;
    }


    public List<UiStrategy> getStrategies() {
        return uiStrategies;
    }

    /**
     * append your custom strategy into the uiStrategies
     *
     * @param customStrategy some custom strategy
     */
    public void addUiStrategies(List<UiStrategy> customStrategy) {
        uiStrategies.addAll(customStrategy);
    }


    /**
     * append your custom strategy into the uiStrategies
     *
     * @param customStrategy custom strategy
     */
    public void addUiStrategies(UiStrategy customStrategy) {
        uiStrategies.add(customStrategy);
    }

}

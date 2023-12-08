package mintychochip.ollivanders.wand.config;

import mintychochip.ollivanders.GenericConfig;

public class WandConfig extends GenericConfig {
    public WandConfig(String fileName) {
        super(fileName);
    }

    @Override
    public void setMain(String path) {
        super.path = path;
        super.main = configReader.getConfigurationSection(path.toUpperCase());
    }
}

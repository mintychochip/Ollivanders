package mintychochip.ollivanders.betterwand.builder;

import mintychochip.ollivanders.Ollivanders;
import mintychochip.ollivanders.betterwand.ComponentType;
import mintychochip.ollivanders.betterwand.WandBoost;
import mintychochip.ollivanders.betterwand.WandConfig;
import mintychochip.ollivanders.betterwand.container.WizardWandData;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class CustomComponentBuilder extends ItemBuilder {

    private final WandConfig wandConfig;
    private final WizardWandData wizardWandData;
    private final String mainPath;

    public CustomComponentBuilder(Material material, String itemPath) {
        super(material);
        this.mainPath = "custom-items." + itemPath;
        wandConfig = Ollivanders.getWandConfig();
        wizardWandData = new WizardWandData();
    }

    public CustomComponentBuilder getComponentType() {
        wizardWandData.setType(getComponent());
        return this;
    }

    public CustomComponentBuilder setComponentType(ComponentType type) {
        wizardWandData.setType(type);
        return this;
    }

    public ComponentType getComponent() {
        String string = wandConfig.getConfigurationSection(mainPath).getString("type");
        ComponentType componentType = ComponentType.valueOf(string != null ? string.toUpperCase() : null);
        return componentType;
    }

    public CustomComponentBuilder getWandBoost() {
        WandBoost wandBoost = new WandBoost();
        ConfigurationSection configurationSection = wandConfig.getConfigurationSection(mainPath + ".modifiers");
        for (String key : configurationSection.getKeys(false)) {
            switch (wandConfig.getWandModifier(key)) {
                case COST -> wandBoost.addCost(configurationSection.getDouble(key));
                case HASTE -> wandBoost.addHaste(configurationSection.getDouble(key));
                case POWER -> wandBoost.addPower(configurationSection.getDouble(key));
                case RANGE -> wandBoost.addRange(configurationSection.getDouble(key));
                case DURATION -> wandBoost.addDuration(configurationSection.getDouble(key));
            }
        }
        wizardWandData.setWandBoost(wandBoost);
        return this;
    }

    public CustomComponentBuilder getDisplayName() {
        String string = wandConfig.getConfigurationSection(mainPath).getString("name");
        wizardWandData.setName(string);
        return this;
    }

    public CustomComponentBuilder setDisplayName(String name) {
        wizardWandData.setName(name);
        return this;
    }

    public CustomComponentBuilder getCore() throws IOException {
        if (!isCore()) {
            throw new IOException("Item path is missing a core component");
        }
        String string = wandConfig.getConfigurationSection(mainPath).getString("core"); //another problem for another day xD
        return this;
    }

    public boolean isCore() {
        return wizardWandData.getType() == ComponentType.CORE || getComponent() == ComponentType.CORE;
    }
    public ItemStack build() {
        return itemStack;
    }
}

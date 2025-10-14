package piotro15.symbiont.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LanguageDatagen extends LanguageProvider {
    public LanguageDatagen(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add("block.symbiont.bioreactor", "Bioreactor");
        add("block.symbiont.metabolizer", "Metabolizer");
        add("block.symbiont.bioformer", "Bioformer");
        add("block.symbiont.recombinator", "Recombinator");

        add("container.bioreactor", "Bioreactor");
        add("container.metabolizer", "Metabolizer");
        add("container.bioformer", "Bioformer");
        add("container.recombinator", "Recombinator");

        add("cell_type.symbiont.proto_cell", "Proto-Cell Culture");
        add("cell_type.symbiont.glucose", "Glucose Cell Culture");

        add("item.symbiont.cell_culture", "Unknown Cell Culture");
        add("item.symbiont.cell_culture.stability", "Stability: ");
        add("item.symbiont.cell_culture.growth", "Growth: ");
        add("item.symbiont.cell_culture.metabolism", "Metabolism: ");
        add("item.symbiont.cell_culture.environment", "Environment: ");
        add("item.symbiont.cell_culture.special", "Special: ");
    }
}

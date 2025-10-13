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

        add("container.bioreactor", "Bioreactor");
        add("container.metabolizer", "Metabolizer");

        add("cell_type.symbiont.proto_cell", "Proto-Cell Culture");
        add("cell_type.symbiont.glucose", "Glucose Cell Culture");
    }
}

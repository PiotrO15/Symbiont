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

        add("cell_type.symbiont.proto", "Proto-Cell Culture");
        add("cell_type.symbiont.glucose", "Glucose Cell Culture");

        add("cell_type.symbiont.ferrum", "Ferrum Cell Culture");
        add("cell_type.symbiont.auric", "Auric Cell Culture");

        add("item.symbiont.cell_culture", "Unknown Cell Culture");
        add("item.symbiont.cell_culture.stability", "Stability: ");
        add("item.symbiont.cell_culture.growth", "Growth: ");
        add("item.symbiont.cell_culture.production", "Production: ");
        add("item.symbiont.cell_culture.consumption", "Consumption: ");
        add("item.symbiont.cell_culture.traits", "Traits:");

        add("item.symbiont.culture_starter", "Culture Starter");

        add("itemGroup.symbiont.common", "Symbiont Common Items");
        add("itemGroup.symbiont.cells", "Symbiont Cells & Traits");

        add("fluid_type.symbiont.nutritional_paste", "Nutritional Paste");
        add("fluid_type.symbiont.sweet_paste", "Sweet Paste");
    }
}

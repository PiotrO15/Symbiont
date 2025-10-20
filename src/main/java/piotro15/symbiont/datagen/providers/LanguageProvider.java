package piotro15.symbiont.datagen.providers;

import net.minecraft.data.PackOutput;

public class LanguageProvider extends net.neoforged.neoforge.common.data.LanguageProvider {
    public LanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add("block.symbiont.bioreactor", "Bioreactor");
        add("block.symbiont.metabolizer", "Metabolizer");
        add("block.symbiont.bioformer", "Bioformer");
        add("block.symbiont.centrifuge", "Centrifuge");
        add("block.symbiont.recombinator", "Recombinator");

        add("container.bioreactor", "Bioreactor");
        add("container.metabolizer", "Metabolizer");
        add("container.bioformer", "Bioformer");
        add("container.centrifuge", "Centrifuge");
        add("container.recombinator", "Recombinator");

        add("symbiont.trait_type.stability", "Stability");
        add("symbiont.trait_type.metabolism", "Metabolism");
        add("symbiont.trait_type.replication", "Replication");
        add("symbiont.trait_type.adaptability", "Adaptability");
        add("symbiont.trait_type.special", "Special");

        add("symbiont.stat.stability", "Stability");
        add("symbiont.stat.growth", "Growth");
        add("symbiont.stat.production", "Production");
        add("symbiont.stat.consumption", "Consumption");
        add("symbiont.stat.type", "Type");

        add("item.symbiont.cell_culture", "Unknown Cell Culture");
        add("item.symbiont.cell_culture.stability", "Stability: ");
        add("item.symbiont.cell_culture.growth", "Growth: ");
        add("item.symbiont.cell_culture.production", "Production: ");
        add("item.symbiont.cell_culture.consumption", "Consumption: ");
        add("item.symbiont.cell_culture.traits", "Traits:");
        add("item.symbiont.cell_culture.prediction", "Recalculated stats:");

        add("item.symbiont.biotrait_extract", "Biotrait Extract");
        add("item.symbiont.organic_binder", "Organic Binder");
        add("item.symbiont.bioplastic_sheet", "Bioplastic Sheet");
        add("item.symbiont.bone_sampler", "Bone Sampler");

        add("itemGroup.symbiont.common", "Symbiont Common Items");
        add("itemGroup.symbiont.cells", "Symbiont Cells & Traits");

        add("fluid_type.symbiont.nutritional_paste", "Nutritional Paste");
        add("fluid_type.symbiont.sweet_paste", "Sweet Paste");
        add("fluid_type.symbiont.protein_paste", "Protein Paste");
        add("fluid_type.symbiont.myogenic_biomass", "Myogenic Biomass");
        add("fluid_type.symbiont.sticky_paste", "Sticky Paste");
        add("fluid_type.symbiont.biopolymer_solution", "Biopolymer Solution");

        add("item.symbiont.nutritional_paste_bucket", "Nutritional Paste Bucket");
        add("item.symbiont.sweet_paste_bucket", "Sweet Paste Bucket");
        add("item.symbiont.protein_paste_bucket", "Protein Paste Bucket");
        add("item.symbiont.myogenic_biomass_bucket", "Myogenic Biomass Bucket");
        add("item.symbiont.biopolymer_solution_bucket", "Biopolymer Solution Bucket");

        add("cell_type.symbiont.proto", "Proto-Cell Culture");
        add("cell_type.symbiont.glucose", "Glucose Cell Culture");
        add("cell_type.symbiont.ferric", "Ferric Cell Culture");
        add("cell_type.symbiont.auric", "Auric Cell Culture");
        add("cell_type.symbiont.poly", "Polycell Culture");
        add("cell_type.symbiont.myoblast", "Myoblast Cell Culture");
        add("cell_type.symbiont.bovine", "Bovine Cell Culture");
        add("cell_type.symbiont.ovine", "Ovine Cell Culture");
        add("cell_type.symbiont.avian", "Avian Cell Culture");
        add("cell_type.symbiont.porcine", "Porcine Cell Culture");
        add("cell_type.symbiont.leporine", "Leporine Cell Culture");

        add("biotrait.symbiont.stable_division", "Stable Division");
        add("biotrait.symbiont.light_tissue", "Light Tissue");
        add("biotrait.symbiont.fat_layered", "Fat-Layered");
        add("biotrait.symbiont.resilient_membrane", "Resilient Membrane");
        add("biotrait.symbiont.resource_aggression", "Resource Aggression");
    }
}

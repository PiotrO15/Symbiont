package piotro15.symbiont.datagen.providers;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import piotro15.symbiont.client.SymbiontClient;
import piotro15.symbiont.common.Symbiont;
import piotro15.symbiont.common.registry.ModItems;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        SymbiontClient.fluidTypeExtensions.forEach((fluid, extension) -> withExistingParent(fluid.get().getBucket().toString(), Symbiont.id("item/generic_bucket")));

        withExistingParent("metabolizer", Symbiont.id("block/metabolizer"));
        withExistingParent("bioreactor", Symbiont.id("block/bioreactor"));
        withExistingParent("bioformer", Symbiont.id("block/bioformer"));
        withExistingParent("recombinator", Symbiont.id("block/recombinator"));

        basicItem(ModItems.CELL_CULTURE.get());
        basicItem(ModItems.ORGANIC_BINDER.get());
        basicItem(ModItems.BIOPLASTIC_SHEET.get());
        handheldItem(ModItems.BONE_SAMPLER.get());
        basicItem(ModItems.BIOTRAIT_EXTRACT.get());

        cellCultureItem(Symbiont.id("proto"));
        cellCultureItem(Symbiont.id("glucose"));
        cellCultureItem(Symbiont.id("myoblast"));
        cellCultureItem(Symbiont.id("poly"), Symbiont.id("generic"));

        cellCultureItem(Symbiont.id("bovine"), Symbiont.id("animal"));
        cellCultureItem(Symbiont.id("ovine"), Symbiont.id("animal"));
        cellCultureItem(Symbiont.id("avian"), Symbiont.id("animal"));
        cellCultureItem(Symbiont.id("porcine"), Symbiont.id("animal"));
        cellCultureItem(Symbiont.id("leporine"), Symbiont.id("animal"));
    }

    private void cellCultureItem(ResourceLocation itemId) {
        ItemModelBuilder builder = getBuilder("cell_type/" + itemId.getPath());
        builder.parent(new ModelFile.UncheckedModelFile("item/generated"));
        builder.texture("layer0", ResourceLocation.fromNamespaceAndPath(itemId.getNamespace(), "item/cell_type/" + itemId.getPath()));
    }

    private void cellCultureItem(ResourceLocation itemId, ResourceLocation variant) {
        ItemModelBuilder builder = getBuilder("cell_type/" + itemId.getPath());
        builder.parent(new ModelFile.UncheckedModelFile("item/generated"));
        builder.texture("layer0", ResourceLocation.fromNamespaceAndPath(itemId.getNamespace(), "item/cell_type/" + variant.getPath()));
    }
}

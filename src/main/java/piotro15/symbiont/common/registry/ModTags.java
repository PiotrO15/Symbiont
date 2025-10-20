package piotro15.symbiont.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import piotro15.symbiont.common.Symbiont;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> CENTRIFUGE_CATALYSTS = createItemTag("centrifuge_catalysts");
        public static final TagKey<Item> STABILITY_CATALYSTS = createItemTag("stability_catalysts");
        public static final TagKey<Item> METABOLISM_CATALYSTS = createItemTag("metabolism_catalysts");
        public static final TagKey<Item> REPLICATION_CATALYSTS = createItemTag("replication_catalysts");
        public static final TagKey<Item> ADAPTABILITY_CATALYSTS = createItemTag("adaptability_catalysts");
        public static final TagKey<Item> SPECIAL_CATALYSTS = createItemTag("special_catalysts");
    }

    private static <T> TagKey<T> createTag(ResourceKey<? extends Registry<T>> registry, String name) {
        return TagKey.create(registry, Symbiont.id(name));
    }

    public static TagKey<Item> createItemTag(String name) {
        return createTag(Registries.ITEM, name);
    }
}

package piotro15.symbiont.common.genetics;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public interface GenericTraitModifier {
    ResourceLocation id();

    Component getDisplayComponent();
}

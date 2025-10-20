package piotro15.symbiont.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import piotro15.symbiont.common.genetics.Biotrait;
import piotro15.symbiont.common.registry.ModDataComponents;
import piotro15.symbiont.common.registry.ModRegistries;

import java.util.List;

public class BiotraitExtractItem extends Item {
    public BiotraitExtractItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        ResourceLocation traitId = stack.get(ModDataComponents.BIOTRAIT);

        if (traitId == null)
            return;

        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection == null)
            return;

        Registry<Biotrait> registry = connection.registryAccess().registryOrThrow(ModRegistries.BIOTRAIT);
        Biotrait biotrait = registry.get(traitId);

        if (biotrait != null) {
            tooltipComponents.add(Component.translatable("biotrait." + traitId.getNamespace() + "." + traitId.getPath())
                    .withColor(0x9067c6));

            tooltipComponents.add(Component.translatable("symbiont.stat.type").withStyle(ChatFormatting.GRAY)
                    .append(Component.literal(": ").withStyle(ChatFormatting.GRAY))
                    .append(biotrait.type().getDisplayName()));

            biotrait.modifiers().forEach(modifier ->
                    tooltipComponents.add(modifier.getDisplayComponent()));
        }
    }
}

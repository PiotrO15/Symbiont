package piotro15.symbiont.api;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

public class FluidApi {
    public record CombinedFluidHandler(FluidTank input, FluidTank output) implements IFluidHandler {

        @Override
        public int getTanks() {
            return 2;
        }

        @Override
        public @NotNull FluidStack getFluidInTank(int tank) {
            return tank == 0 ? input.getFluid() : tank == 1 ? output.getFluid() : FluidStack.EMPTY;
        }

        @Override
        public int getTankCapacity(int tank) {
            return tank == 0 ? input.getCapacity() : tank == 1 ? output.getCapacity() : 0;
        }

        @Override
        public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
            // simple: both tanks accept any fluid. Change this if you want restrictions.
            return true;
        }

        @Override
        public int fill(@NotNull FluidStack resource, @NotNull FluidAction action) {
            if (resource.isEmpty()) return 0;
            // only allow fills into the input tank
            return input.fill(resource, action);
        }

        @Override
        public @NotNull FluidStack drain(@NotNull FluidStack resource, @NotNull FluidAction action) {
            if (resource.isEmpty()) return FluidStack.EMPTY;
            // only allow drains from the output tank when the fluid matches
            FluidStack out = output.getFluid();
            if (FluidStack.isSameFluid(resource, out)) {
                return output.drain(resource.getAmount(), action);
            }
            return FluidStack.EMPTY;
        }

        @Override
        public @NotNull FluidStack drain(int maxDrain, @NotNull FluidAction action) {
            // only drain from output tank
            return output.drain(maxDrain, action);
        }
    }
}
